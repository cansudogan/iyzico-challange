package com.iyzico.challenge.repository;

import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.entity.UserPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
}
