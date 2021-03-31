package com.iyzico.challenge.entity;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;

import com.iyzico.challenge.entity.validator.BankCard;
import com.iyzico.challenge.entity.validator.CVC;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.YearMonth;

public class PaymentRequest {

    @NotNull
    @Length(min = 5)
    private String holderName;

    @NotNull
    private String cardNumber;

    @NotNull
    @JsonDeserialize(using = YearMonthDeserializer.class)
    private YearMonth expire;

    @CVC
    @NotNull
    private String cvc;

    @NotNull
    @Min(1)
    @Max(32)
    private int installment;

    private long userId;

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public YearMonth getExpire() {
        return expire;
    }

    public void setExpire(YearMonth expire) {
        this.expire = expire;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public int getInstallment() {
        return installment;
    }

    public void setInstallment(int installment) {
        this.installment = installment;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}