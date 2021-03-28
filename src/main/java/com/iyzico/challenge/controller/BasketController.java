package com.iyzico.challenge.controller;

import com.iyzico.challenge.entity.Basket;
import com.iyzico.challenge.service.basketService.BasketService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class BasketController {

    private BasketService basketService;

    public BasketController(BasketService basketService) {
        this.basketService = basketService;
    }

    @RequestMapping(value = "/retrieveBasketByUserId/{id}", method = RequestMethod.GET)
    public Basket retrieveBasketByUserId(@PathVariable long id) {

            return basketService.retrieveBasketByUserId(id);

    }
}
