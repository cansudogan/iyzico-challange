package com.iyzico.challenge.service.basketService;

import com.iyzico.challenge.entity.Basket;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.entity.ProductsInBasket;
import com.iyzico.challenge.entity.User;
import com.iyzico.challenge.repository.BasketRepository;
import com.iyzico.challenge.service.productService.ProductService;
import com.iyzico.challenge.service.userService.UserService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Iterator;

@Service
@Transactional
public class BasketServiceImpl implements BasketService {

    private BasketRepository basketRepository;
    private ProductService productService;
    private UserService userService;

    public BasketServiceImpl(BasketRepository basketRepository, ProductService productService, UserService userService) {
        this.basketRepository = basketRepository;
        this.productService = productService;
        this.userService = userService;
    }

    @Override
    public Basket retrieveBasketByUserId(long userId) {
        User user = userService.retrieveUserById(userId);
        return basketRepository.findOneByUser(user);
    }

    @Override
    public Basket addProductToBasket(long userId, long productId, int count) {

        Product product = productService.retrieveProductById(productId);
        if (product.getRemainingStock() == 0) {
            throw new IllegalStateException("mal yok");
        }

        if (product.getRemainingStock() < count) {
            throw new IllegalStateException("mal az");
        }

        Basket basket = basketRepository.findOneByUserId(userId);

        for (ProductsInBasket productsInBasket : basket.getProducts()) {
            if (productsInBasket.getProductId() == productId) {
                productsInBasket.setCount(productsInBasket.getCount() + count);
                return basketRepository.save(basket);
            }
        }

        ProductsInBasket productsInBasket = new ProductsInBasket();

        productsInBasket.setBasket(basket);
        productsInBasket.setProduct(product);
        productsInBasket.setCount(count);
        productsInBasket.setCreateTime(LocalDateTime.now());
        basket.getProducts().add(productsInBasket);

        return basketRepository.save(basket);

    }

    @Override
    public Basket removeProductFromBasket(long userId, long productId, int count) {
        Basket basket = basketRepository.findOneByUserId(userId);
        Iterator<ProductsInBasket> iterator = basket.getProducts().iterator();
        while (iterator.hasNext()) {
            ProductsInBasket next = iterator.next();
            if (next.getId() == productId) {
                iterator.remove();
                basketRepository.save(basket);
            }
        }

        return basket;
    }

    @Override
    public void complete(Basket basket) {
        basket.setStatus(Basket.Status.COMPLETED);
        basketRepository.save(basket);
    }

}
