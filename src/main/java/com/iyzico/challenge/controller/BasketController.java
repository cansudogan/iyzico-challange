package com.iyzico.challenge.controller;

import com.iyzico.challenge.entity.AddProductToBasketRequest;
import com.iyzico.challenge.entity.Basket;
import com.iyzico.challenge.entity.RemoveProductFromBasket;
import com.iyzico.challenge.service.basketService.BasketService;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/addProductToBasket",method = RequestMethod.PUT, consumes = MediaType.ALL_VALUE)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Basket add(@RequestBody AddProductToBasketRequest request) {
        return basketService.addProductToBasket(request.getUserId(), request.getProductId(), request.getCount());
    }

    @RequestMapping(value = "/removeProductFromBasket",method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Basket remove(@RequestBody RemoveProductFromBasket request) {
        return basketService.removeProductFromBasket(request.getUserId(), request.getProductId(), request.getCount());
    }
}
