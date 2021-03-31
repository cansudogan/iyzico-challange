package com.iyzico.challenge.entity.validator;



import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BankCardValidator implements ConstraintValidator<BankCard, String> {


    @Override
    public void initialize(BankCard constraintAnnotation) {

    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return false;
    }
}