package com.iyzico.challenge.service;

import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class IyzicoPaymentService {

    private Logger logger = LoggerFactory.getLogger(IyzicoPaymentService.class);

    private BankService bankService;
    private PaymentRepository paymentRepository;

    public IyzicoPaymentService(BankService bankService, PaymentRepository paymentRepository) {
        this.bankService = bankService;
        this.paymentRepository = paymentRepository;
    }

    public void pay(BigDecimal price) {
        Payment inner = new Payment();
        inner.setPrice(price);
        inner.setStatus(Payment.Status.IN_PROGRESS);
        Payment payment = paymentRepository.saveAndFlush(inner);

        //pay with bank
        BankPaymentRequest request = new BankPaymentRequest();
        request.setPrice(price);
        BankPaymentResponse response = bankService.pay(request);

        Payment.Status status;
        String message = null;
        try {
            response = bankService.pay(request);
            status = Payment.Status.SUCCESS;
        } catch (Throwable t) {
            logger.warn("Unexpected exception during the payment", t);
            message = t.getMessage();
            status = Payment.Status.ERROR;
        }

        try {
            String finalMessage = message;
            BankPaymentResponse finalResponse = response;
            Payment.Status finalStatus = status;

            for (int i = 0; i < 10; i++) {
                try {
                    payment.setBankResponse(finalResponse == null ? finalMessage : finalResponse.getResultCode());
                    payment.setStatus(finalStatus);
                    paymentRepository.saveAndFlush(payment);
                    break;
                } catch (Throwable t) {
                    if (i == 9) {
                        throw t;
                    }
                }
            }
        } catch (Throwable t) {
            // queue it to retry or send a notification to the operation department to review it and fix the problem manually
            logger.warn("Unexpected exception while saving state of payment. status: {}", status, t);
            throw t;
        }

        logger.info("Payment saved successfully!");
    }
    }

