package com.iyzico.challenge.controller;

import com.iyzico.challenge.entity.InstallmentDTO;
import com.iyzico.challenge.entity.Payment;
import com.iyzico.challenge.entity.PaymentRequest;
import com.iyzico.challenge.service.paymentService.PaymentService;
import com.iyzico.challenge.util.HttpUtils;
import com.iyzipay.model.InstallmentDetail;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(value = "installments/{digits:.{6}}/{userId}", method = RequestMethod.GET)
    public InstallmentDTO getInstallments(@PathVariable("digits") String digits, @PathVariable("userId") long userId) {
        InstallmentDetail details = paymentService.getInstallments(digits, userId);
        return map(details);
    }
    @RequestMapping(method = RequestMethod.PUT)
    public com.iyzipay.model.Payment pay(@RequestBody @Valid PaymentRequest request,
                       HttpServletRequest servletRequest) {

        return paymentService.pay(
                request.getUserId(),
                request.getHolderName(),
                request.getCardNumber(),
                request.getExpire(),
                request.getCvc(),
                HttpUtils.getClientIp(servletRequest),
                request.getInstallment());
    }
    private InstallmentDTO map(InstallmentDetail details) {
        InstallmentDTO dto = new InstallmentDTO();
        dto.setCardType(details.getCardType());
        dto.setCardAssociation(details.getCardAssociation());
        dto.setBankCode(details.getBankCode());
        dto.setBankName(details.getBankName());
        dto.setForce3ds(int2bool(details.getForce3ds()));
        dto.setCommercial(int2bool(details.getCommercial()));
        dto.setForceCvc(int2bool(details.getForceCvc()));
        dto.setPrices(details.getInstallmentPrices()
                .stream()
                .map(x -> {
                    InstallmentDTO.InstallmentPrice price = new InstallmentDTO.InstallmentPrice();
                    price.setInstallment(x.getInstallmentPrice());
                    price.setTotal(x.getTotalPrice());
                    price.setCount(x.getInstallmentNumber());
                    return price;
                })
                .collect(Collectors.toList()));
        return dto;
    }
    private boolean int2bool(Integer integer) {
        return integer != null && integer != 0;
    }
}
