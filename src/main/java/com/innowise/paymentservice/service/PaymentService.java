package com.innowise.paymentservice.service;

import com.innowise.paymentservice.model.dto.CreatePaymentRequest;
import com.innowise.paymentservice.model.dto.PaymentResponse;
import com.innowise.paymentservice.model.entity.PaymentStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
public interface PaymentService {

    PaymentResponse create(CreatePaymentRequest request);

    List<PaymentResponse> getPayments(Long userId, Long orderId, PaymentStatus status);

    BigDecimal getCurrentUserTotal(Long userId, Instant from, Instant to);

    BigDecimal getAllUsersTotal(Instant from, Instant to);
}
