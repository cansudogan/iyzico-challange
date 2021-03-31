package com.iyzico.challenge.service.productService;

import com.iyzico.challenge.entity.CreateProductRequest;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.entity.UpdateProductRequest;

import java.util.List;


public interface ProductService {
    Product create(CreateProductRequest createProductRequest);
    Product retrieveProductById(Long id);
    void removeProductById(Long id);
    Product update(UpdateProductRequest updateProductRequest);
    List<Product> retrieveAllProducts();
}
