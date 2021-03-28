package com.iyzico.challenge.service.productService;

import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.entity.UpdateProductRequest;
import com.iyzico.challenge.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void create(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product retrieveProductById(Long id) {
        Optional<Product> selectedProduct = productRepository.findById(id);
        return selectedProduct.get();
    }

    @Override
    public void removeProductById(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void update(UpdateProductRequest updateProductRequest) {
        Product updatedProduct = retrieveProductById(updateProductRequest.getProductId());

        updatedProduct.setProductName(updateProductRequest.getProductName());
        updatedProduct.setProductDescription(updateProductRequest.getProductDescription());
        updatedProduct.setRemainingStock(updateProductRequest.getRemainingStock());
        updatedProduct.setProductPrice(updateProductRequest.getProductPrice());

        productRepository.save(updatedProduct);
    }

    @Override
    public List<Product> retrieveAllProducts() {
        return productRepository.findAll();
    }


}
