package com.iyzico.challenge.service.productService;

import com.iyzico.challenge.dao.ProductDAO;
import com.iyzico.challenge.entity.Product;
import com.iyzico.challenge.entity.UpdateProductRequest;
import com.iyzico.challenge.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private ProductRepository productRepository;


    @Override
    public void create(Product product) {
        productRepository.save(product);
    }

    @Override
    public Product get(Long id) {
        Optional<Product> selectedProduct = productRepository.findById(id);
        return selectedProduct.get();
    }

    @Override
    public void remove(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public void update(UpdateProductRequest updateProductRequest) {
        Product updatedProduct = get(updateProductRequest.getProductId());

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
