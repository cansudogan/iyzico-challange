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

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String retrieveProductById(@PathVariable long id) {
        logger.info("Getting product with id " + id);
        try {
            Product product = productService.retrieveProductById(id);
            return product.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public String create(@RequestBody CreateProductRequest createProductRequest) {

        logger.info("New product created " + createProductRequest.getProductName(), createProductRequest.getProductDescription(),
                createProductRequest.getRemainingStock(), createProductRequest.getProductPrice());
        try {
            Product product = new Product(createProductRequest.getProductName(), createProductRequest.getProductDescription(),
                    createProductRequest.getRemainingStock(), createProductRequest.getProductPrice());
            productService.create(product);
            return "Added product with id: " + product.getProductId();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    public String removeProductById(@PathVariable long id) {
        logger.info("Deleted product with id " + id);
        try {
            productService.removeProductById(id);
            return "id: " + id;
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public String update(@RequestBody UpdateProductRequest updateProductRequest) {

        logger.info("Product updated " + updateProductRequest.getProductName(), updateProductRequest.getProductDescription(),
                updateProductRequest.getRemainingStock(), updateProductRequest.getProductPrice());
        try {
            productService.update(updateProductRequest);
            return "";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/retrieveAllProducts", method = RequestMethod.GET)
    public List<Product> retrieveAllProducts() {
        return productService.retrieveAllProducts();
    }


}
