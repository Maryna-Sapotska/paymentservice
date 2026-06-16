package com.innowise.paymentservice.model.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreatePaymentRequest {

    private Long orderId;

    private BigDecimal paymentAmount;
}
