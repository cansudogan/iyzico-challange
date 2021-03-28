package com.iyzico.challenge.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AddProductToBasketRequest {
    @Min(0)
    @NotNull
    private long productId;
    @Min(0)
    @NotNull
    private int count;
    @Min(0)
    @NotNull
    private long userId;

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
