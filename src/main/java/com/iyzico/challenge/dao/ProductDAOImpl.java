package com.iyzico.challenge.dao;

import com.iyzico.challenge.entity.Product;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
@Repository
public class ProductDAOImpl implements ProductDAO{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void create(Product product) {
        entityManager.persist(product);
    }

    @Override
    public void update(Product product) {
        entityManager.merge(product);
    }

    @Override
    public Product getProductById(long id) {
        return entityManager.find(Product.class, id);
    }

    @Override
    public void delete(long id) {
        Product product = getProductById(id);

        if (product != null)
            entityManager.remove(product);

    }
}
