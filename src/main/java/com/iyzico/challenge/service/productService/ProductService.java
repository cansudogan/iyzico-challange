package com.iyzico.challenge.service.productService;

import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.entity.UpdateProductRequest;

import java.util.List;


public interface ProductService {
    void create(Product product);
    Product get(Long id);

    void remove(Long id);

    void update(UpdateProductRequest updateProductRequest);

    List<Product> retrieveAllProducts();
}
