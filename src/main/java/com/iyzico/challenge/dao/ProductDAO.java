package com.iyzico.challenge.dao;

import com.iyzico.challenge.entity.Product;

public interface ProductDAO {
    void create(Product product);

    void update(Product product);

    Product getProductById(long id);

    void remove(long id);
}
