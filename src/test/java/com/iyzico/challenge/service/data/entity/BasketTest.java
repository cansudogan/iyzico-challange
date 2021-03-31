package com.iyzico.challenge.service.data.entity;

import com.iyzico.challenge.entity.Basket;
import com.iyzico.challenge.entity.ProductsInBasket;
import com.iyzico.challenge.entity.User;
import mockit.Mocked;
import mockit.StrictExpectations;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Collections;
import java.util.Set;

@RunWith(JMockit.class)
public class BasketTest {
    @Tested
    private Basket tested;

    @Test
    public void mapping_test(@Mocked User user) {
        long id = 1;
        long userId = 1;
        Basket.Status status = Basket.Status.APPLIED;
        Set<ProductsInBasket> products = Collections.unmodifiableSet(Collections.emptySet());
        new StrictExpectations() {{
        }};

        tested.setId(id);
        tested.setStatus(status);
        tested.setProducts(products);
        tested.setUser(user);
        tested.setUserId(userId);

        Assert.assertEquals(id, tested.getId());
        Assert.assertEquals(userId, tested.getUserId());
        Assert.assertEquals(status, tested.getStatus());
        Assert.assertEquals(user, tested.getUser());
        Assert.assertEquals(products, tested.getProducts());
    }
}