package com.iyzico.challenge;

import com.iyzico.challenge.entity.CreateProductRequest;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.service.productService.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@EnableAutoConfiguration
public class Controller {

    private static final Logger logger = LoggerFactory.getLogger(Controller.class);

    private ProductService productService;

    public Controller(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String get(@PathVariable long id) {

        logger.info("Getting product with id " + id);
        try {
            Product product = productService.get(id);
            return  product.toString();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
    public String post(@RequestBody CreateProductRequest createProductRequest) {

        logger.info("New product created " + createProductRequest.getProductName(), createProductRequest.getProductDescription(),
                createProductRequest.getRemainingStock(), createProductRequest.getProductPrice());
        try {
            Product product = new Product(createProductRequest.getProductName(), createProductRequest.getProductDescription(),
                    createProductRequest.getRemainingStock(), createProductRequest.getProductPrice());
            productService.create(product);
            return "Added employee with id: " + product.getProductId();
        } catch (Exception e) {
            return e.getMessage();
        }
    }
}
