package com.iyzico.challenge.service.productService;

import com.iyzico.challenge.dao.ProductDAO;
import com.iyzico.challenge.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class ProductServiceImpl implements ProductService{
    @Autowired
    private ProductDAO productDAO;


    @Override
    public void create(Product product) {
        productDAO.create(product);
    }

    @Override
    public Product get(Long id) {
        return productDAO.getProductById(id);
    }
}
