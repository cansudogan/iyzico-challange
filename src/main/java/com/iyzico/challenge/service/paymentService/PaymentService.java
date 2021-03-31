package com.iyzico.challenge.service.paymentService;

import com.iyzico.challenge.entity.Payment;
import com.iyzipay.model.InstallmentDetail;

import java.time.YearMonth;

public interface PaymentService {

    InstallmentDetail getInstallments(String digits, long userId);

    com.iyzipay.model.Payment pay(long userId, String holderName, String cardNumber, YearMonth expire, String cvc, String clientIp, int installment);
}
