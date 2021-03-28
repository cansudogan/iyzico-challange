package com.iyzico.challenge.service.basketService;

import com.iyzico.challenge.entity.Basket;
import com.iyzico.challenge.repository.BasketRepository;
import org.springframework.stereotype.Service;

@Service
public class BasketServiceImpl implements BasketService{

    private BasketRepository basketRepository;

    public BasketServiceImpl(BasketRepository basketRepository) {
        this.basketRepository = basketRepository;
    }

    @Override
    public Basket retrieveBasketByUserId(long userId) {
        return basketRepository.findOneByUserId(userId);
    }
}
