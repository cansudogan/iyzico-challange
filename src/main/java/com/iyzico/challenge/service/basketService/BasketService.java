package com.iyzico.challenge.service.basketService;

import com.iyzico.challenge.entity.Basket;

public interface BasketService {
    Basket retrieveBasketByUserId(long userId);
}
