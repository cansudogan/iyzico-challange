package com.iyzico.challenge.service.paymentService;

import com.iyzico.challenge.entity.*;
import com.iyzico.challenge.properties.IyzicoProperties;
import com.iyzico.challenge.repository.PaymentRepository;
import com.iyzico.challenge.repository.UserPaymentRepository;
import com.iyzico.challenge.service.UserOrderService;
import com.iyzico.challenge.service.basketService.BasketService;
import com.iyzico.challenge.service.userService.UserService;
import com.iyzipay.IyzipayResource;
import com.iyzipay.Options;
import com.iyzipay.model.*;
import com.iyzipay.request.CreatePaymentRequest;
import com.iyzipay.request.RetrieveInstallmentInfoRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService{
    private final String COUNTRY = "TR";
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final Options options;

    private UserService userService;
    private BasketService basketService;
    private PaymentRepository paymentRepository;
    private UserPaymentRepository userPaymentRepository;
    private final UserOrderService userOrderService;
    public PaymentServiceImpl(UserPaymentRepository userPaymentRepository,
                              UserOrderService userOrderService,
                              PaymentRepository paymentRepository,
                              UserService userService,
                              BasketService basketService,
                              IyzicoProperties properties) {
        this.userPaymentRepository = userPaymentRepository;
        this.userOrderService = userOrderService;
        this.paymentRepository = paymentRepository;
        this.userService = userService;
        this.basketService = basketService;
        Options options = new Options();
        options.setBaseUrl(properties.getBaseUrl());
        options.setApiKey(properties.getApiKey());
        options.setSecretKey(properties.getSecretKey());
        this.options = options;
    }

    @Override
    public InstallmentDetail getInstallments(String digits, long userId) {
        User user = userService.retrieveUserById(userId);
        Basket basket = basketService.retrieveBasketByUserId(userId);
        return getInstallments(user, basket, digits);
    }

    @Override
    public com.iyzipay.model.Payment pay(long userId, String holderName, String cardNumber, YearMonth expire, String cvc, String clientIp, int installment) {
        return doPay(userId, holderName, cardNumber, expire, cvc, clientIp, installment);
    }

    private com.iyzipay.model.Payment doPay(long userId, String holderName, String cardNumber, YearMonth expire, String cvc, String clientIp, int installmentCount) {
        Basket basket = null;

        User profile;
        InstallmentPrice installment;
        UserPayment payment;
        boolean decreased = false;
        try {

            profile = userService.retrieveUserById(userId);

            basket = basketService.retrieveBasketByUserId(userId);

            installment = validateAndGetInstallment(profile, basket, cardNumber, installmentCount);

            //basket = basketService.decreaseStocks(profile, basket);
            decreased = true;

            payment = startPayment(profile, basket);
        } catch (Throwable t) {
            if (decreased) {
                try {
                    //basketService.rollbackStocks(user, basket);
                } catch (Throwable t2) {
                    throw t2;
                }
            }

            throw t;
        }

        com.iyzipay.model.Payment response;
        try {
            response = sendPaymentRequest(profile, basket, profile, installment, holderName, cardNumber, expire, cvc, clientIp);
        } catch (Throwable t) {
            try {
                markAsFailure(payment, t.getMessage());
            } catch (Throwable t2) {
                throw t2;
            }
            throw t;
        }

        try {
            markAsSuccess(profile, payment, basket, response);
        } catch (Throwable t) {
        }

        return response;

    }

    private void markAsSuccess(User profile, UserPayment payment, Basket basket, com.iyzipay.model.Payment response) {
        payment.setStatus(UserPayment.Status.SUCCESS);
        payment.setPaymentGatewayId(response.getPaymentId());
        userOrderService.create(profile, payment, basket);
        basketService.complete(basket);
        userPaymentRepository.save(payment);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    private void markAsFailure(UserPayment payment, String message) {
        payment.setStatus(UserPayment.Status.ERROR);
        userPaymentRepository.save(payment);
    }

    private com.iyzipay.model.Payment sendPaymentRequest(User profile, Basket basket, User profile1, InstallmentPrice installment, String holderName, String cardNumber, YearMonth expire, String cvc, String clientIp) {
        CreatePaymentRequest request = new CreatePaymentRequest();
        request.setLocale(COUNTRY);
        request.setConversationId(String.valueOf(basket.getId()));
        request.setPrice(basket.getTotal());
        request.setCurrency(Currency.TRY.name());
        request.setBasketId(String.valueOf(basket.getId()));
        request.setPaymentChannel(PaymentChannel.WEB.name());
        request.setPaymentGroup(PaymentGroup.PRODUCT.name());

        request.setPaidPrice(installment.getTotalPrice());
        request.setInstallment(installment.getInstallmentNumber());

        PaymentCard paymentCard = new PaymentCard();
        paymentCard.setCardHolderName(holderName);
        paymentCard.setCardNumber(cardNumber);
        paymentCard.setExpireMonth(String.valueOf(expire.getMonthValue()));
        paymentCard.setExpireYear(String.valueOf(expire.getYear()));
        paymentCard.setCvc(cvc);
        paymentCard.setRegisterCard(0);
        request.setPaymentCard(paymentCard);

        Buyer buyer = new Buyer();
        buyer.setId(String.valueOf(profile.getId()));
        buyer.setName(profile.getName());
        buyer.setSurname(profile.getSurname());
        buyer.setGsmNumber(profile.getPhoneNumber());
        buyer.setEmail(profile.getEmail());
        buyer.setIdentityNumber(profile.getIdentityNo());
        buyer.setLastLoginDate(formatter.format(profile.getRegistrationDate()));
        buyer.setRegistrationDate(formatter.format(profile.getRegistrationDate()));
        buyer.setRegistrationAddress(profile.getAddress());
        buyer.setIp(clientIp);
        buyer.setCity(profile.getCity());
        buyer.setCountry(profile.getCountry());
        buyer.setZipCode(profile.getZipCode());
        request.setBuyer(buyer);

        java.util.Locale countryLocale = new java.util.Locale("", profile.getCountry());
        Address address = new Address();
        address.setContactName(String.format("%s %s", profile.getName(), profile.getSurname()));
        address.setCity(profile.getCity());
        address.setCountry(countryLocale.getDisplayCountry());
        address.setAddress(profile.getAddress());
        address.setZipCode(profile.getZipCode());
        request.setShippingAddress(address);

        request.setBillingAddress(address);

        List<BasketItem> basketItems = new ArrayList<>(basket.getProducts().size());
        for (ProductsInBasket product : basket.getProducts()) {
            BasketItem item = new BasketItem();
            item.setId(String.valueOf(product.getProductId()));
            item.setName(product.getProduct().getProductName());
            item.setCategory1("No category");
            item.setItemType(BasketItemType.PHYSICAL.name());
            item.setPrice(product.getBasket().getTotal());
            basketItems.add(item);
        }

        request.setBasketItems(basketItems);

        return validate(com.iyzipay.model.Payment.create(request, options));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Throwable.class)
    private UserPayment startPayment(User profile, Basket basket) {
        UserPayment payment = new UserPayment();
        payment.setBasket(basket);
        payment.setUser(profile);
        payment.setAmount(basket.getTotal());
        payment.setStatus(UserPayment.Status.IN_PROGRESS);
        payment.setCreateTime(LocalDateTime.now());
        payment.setProducts(
                basket.getProducts()
                        .stream()
                        .map(x -> {
                            PaymentProduct product = new PaymentProduct();
                            product.setPayment(payment);
                            product.setProduct(x.getProduct());
                            product.setCount(x.getCount());
                            product.setPrice(x.getProduct().getProductPrice());
                            return product;
                        })
                        .collect(Collectors.toSet()));

        userPaymentRepository.save(payment);
        return payment;
    }

    private InstallmentPrice validateAndGetInstallment(User profile, Basket basket, String cardNumber, int installmentCount) {
        InstallmentDetail installmentInfo = getInstallments(profile, basket, cardNumber.substring(0, 6));
        Optional<InstallmentPrice> installmentResult = installmentInfo.getInstallmentPrices()
                .stream()
                .filter(x -> x.getInstallmentNumber() == installmentCount)
                .findFirst();

        if (!installmentResult.isPresent()) {
            throw new IllegalStateException("Invalid installment");
        }

        return installmentResult.get();
    }

    private InstallmentDetail getInstallments(User user, Basket basket, String digits) {
        RetrieveInstallmentInfoRequest request = new RetrieveInstallmentInfoRequest();
        request.setLocale(COUNTRY);
        request.setBinNumber(digits);
        request.setPrice(basket.getTotal());
        request.setCurrency(Currency.TRY.name());
        request.setConversationId(String.valueOf(basket.getId()));

        InstallmentInfo result = InstallmentInfo.retrieve(request, options);
        validate(result);

        if (result.getInstallmentDetails().isEmpty()) {
            throw new IllegalStateException(String.format("No installment info found for digits %s", digits));
        }
        return result.getInstallmentDetails().get(0);
    }

    private <T extends IyzipayResource> T validate(T result) {
        if (!"success".equalsIgnoreCase(result.getStatus())) {
            throw new IllegalStateException(String.format("%s: %s", result.getErrorCode(), result.getErrorMessage()));
        }

        return result;
    }
}
