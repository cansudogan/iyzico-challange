package com.iyzico.challenge.repository;

import com.iyzico.challenge.entity.UserPayment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPaymentRepository extends CrudRepository<UserPayment, Long> {
}
