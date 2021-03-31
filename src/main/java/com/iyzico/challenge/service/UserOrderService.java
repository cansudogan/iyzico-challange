package com.iyzico.challenge.service;


import com.iyzico.challenge.entity.Basket;
import com.iyzico.challenge.entity.User;
import com.iyzico.challenge.entity.UserOrder;
import com.iyzico.challenge.entity.UserPayment;
import com.iyzico.challenge.repository.UserOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class UserOrderService {
    private final UserOrderRepository repository;

    public UserOrderService(UserOrderRepository repository) {
        this.repository = repository;
    }

    @Transactional(propagation = Propagation.MANDATORY, rollbackFor = Throwable.class)
    public UserOrder create(User user, UserPayment payment, Basket basket) {
        UserOrder order = new UserOrder();
        order.setBasket(basket);
        order.setPayment(payment);
        order.setUser(user);
        order.setCreateTime(LocalDateTime.now());
        repository.save(order);
        return order;
    }
}
