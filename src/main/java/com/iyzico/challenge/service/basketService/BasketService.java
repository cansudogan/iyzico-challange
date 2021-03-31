package com.iyzico.challenge.service.basketService;

import com.iyzico.challenge.entity.Basket;

public interface BasketService {
    Basket retrieveBasketByUserId(long userId);
    Basket addProductToBasket(long userId ,long productId, int count);
    Basket removeProductFromBasket(long userId ,long productId, int count);

    void complete(Basket basket);
}
