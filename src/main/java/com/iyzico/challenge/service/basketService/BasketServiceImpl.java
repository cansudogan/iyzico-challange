package com.iyzico.challenge.service.basketService;

import com.iyzico.challenge.entity.Basket;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.entity.ProductsInBasket;
import com.iyzico.challenge.repository.BasketRepository;
import com.iyzico.challenge.service.productService.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class BasketServiceImpl implements BasketService {

    private BasketRepository basketRepository;
    private ProductService productService;

    public BasketServiceImpl(BasketRepository basketRepository, ProductService productService) {
        this.basketRepository = basketRepository;
        this.productService = productService;
    }

    @Override
    public Basket retrieveBasketByUserId(long userId) {
        return basketRepository.findOneByUserId(userId);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Throwable.class)
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
                basket.setTotalPrice(basket.getTotalPrice().add(product.getProductPrice().
                        multiply(BigDecimal.valueOf(count))));
                return basketRepository.save(basket);
            }
        }

        ProductsInBasket productsInBasket = new ProductsInBasket();

        productsInBasket.setBasket(basket);
        productsInBasket.setProduct(product);
        productsInBasket.setCount(count);
        productsInBasket.setCreateTime(LocalDateTime.now());
        basket.setTotalPrice(basket.getTotalPrice().add(product.getProductPrice().multiply(BigDecimal.valueOf(count))));

        basket.getProducts().add(productsInBasket);

        return basketRepository.save(basket);

    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Throwable.class)
    public Basket removeProductFromBasket(long userId, long productId, int count){
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
                productsInBasket.setCount(productsInBasket.getCount() - count);
                basket.setTotalPrice(basket.getTotalPrice().subtract(product.getProductPrice().
                        multiply(BigDecimal.valueOf(count))));

                return basketRepository.save(basket);
            }
        }

        ProductsInBasket productsInBasket = new ProductsInBasket();

        productsInBasket.setBasket(basket);
        productsInBasket.setProduct(product);
        productsInBasket.setCount(count);
        productsInBasket.setCreateTime(LocalDateTime.now());
        basket.setTotalPrice(basket.getTotalPrice().subtract(
                product.getProductPrice().multiply(BigDecimal.valueOf(count))));

        basket.getProducts().remove(productsInBasket);

        return basketRepository.save(basket);
    }
}
