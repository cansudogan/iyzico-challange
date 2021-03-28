package com.iyzico.challenge.service.productService;

import com.iyzico.challenge.entity.Product;

public interface ProductService {
    void create(Product product);
    Product get(Long id);
}
