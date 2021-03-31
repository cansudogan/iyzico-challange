package com.iyzico.challenge.controller;

import com.iyzico.challenge.entity.CreateProductRequest;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.entity.UpdateProductRequest;
import com.iyzico.challenge.service.productService.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/retrieveProduct/{id}", method = RequestMethod.GET)
    public Product retrieveProductById(@PathVariable long id) {
        Product product = productService.retrieveProductById(id);
        return product;
    }

    @RequestMapping(value = "/createProduct", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public Product create(@RequestBody CreateProductRequest createProductRequest) {
        return productService.create(createProductRequest);
    }

    @RequestMapping(value = "/deleteProduct/{id}", method = RequestMethod.DELETE)
    public void removeProductById(@PathVariable long id) {
        productService.removeProductById(id);
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public Product update(@RequestBody UpdateProductRequest updateProductRequest) {
        return productService.update(updateProductRequest);

    }

    @RequestMapping(value = "/retrieveAllProducts", method = RequestMethod.GET)
    public List<Product> retrieveAllProducts() {
        return productService.retrieveAllProducts();
    }


}
