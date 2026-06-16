package com.innowise.paymentservice.model.dto;

import com.innowise.paymentservice.model.entity.PaymentStatus;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder
public class PaymentResponse {

    private String id;

    private Long orderId;

    private Long userId;

    private PaymentStatus status;

    private Instant timestamp;

    private BigDecimal paymentAmount;
}
