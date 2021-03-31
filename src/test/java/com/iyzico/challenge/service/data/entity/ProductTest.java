package com.iyzico.challenge.service.data.entity;

import com.iyzico.challenge.entity.Product;
import mockit.Tested;
import mockit.integration.junit4.JMockit;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.math.BigDecimal;

@RunWith(JMockit.class)
public class ProductTest {
    @Tested
    private Product tested;

    @Test
    public void mapping_test() {
        long id = 1;
        String name = "name";
        long userId = 2;
        int stockCount = 3;
        BigDecimal price = BigDecimal.ONE;
        String description = new String();

        tested.setId(id);
        tested.setProductName(name);
        tested.setProductDescription(description);
        tested.setRemainingStock(stockCount);
        tested.setProductPrice(price);


        Assert.assertEquals(id, tested.getId());
        Assert.assertEquals(name, tested.getProductName());
        Assert.assertEquals(stockCount, tested.getRemainingStock());
        Assert.assertEquals(price, tested.getProductPrice());
        Assert.assertEquals(description, tested.getProductDescription());
    }
}