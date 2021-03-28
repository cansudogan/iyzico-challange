package com.iyzico.challenge.repository;

import com.iyzico.challenge.entity.Basket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasketRepository extends JpaRepository<Basket,Long> {

    Basket findOneByUserId(long userId);
}
