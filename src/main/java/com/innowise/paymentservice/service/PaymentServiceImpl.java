package com.innowise.paymentservice.service;

import com.innowise.paymentservice.client.RandomNumberClient;
import com.innowise.paymentservice.mapper.PaymentMapper;
import com.innowise.paymentservice.model.dto.CreatePaymentRequest;
import com.innowise.paymentservice.model.dto.PaymentResponse;
import com.innowise.paymentservice.model.entity.Payment;
import com.innowise.paymentservice.model.entity.PaymentStatus;
import com.innowise.paymentservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final RandomNumberClient randomNumberClient;

    @Override
    public PaymentResponse create(CreatePaymentRequest request) {

        Payment payment = paymentMapper.toEntity(request);

        Integer randomNumber = randomNumberClient.getRandomNumber();

        payment.setStatus(
                randomNumber % 2 == 0
                        ? PaymentStatus.SUCCESS
                        : PaymentStatus.FAILED
        );

        payment.setTimestamp(Instant.now());

        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toResponse(savedPayment);
    }

    @Override
    public List<PaymentResponse> getPayments(Long userId, Long orderId, PaymentStatus status) {

        List<Payment> payments;

        if (userId != null) {
            payments = paymentRepository.findByUserId(userId);
        } else if (orderId != null) {
            payments = paymentRepository.findByOrderId(orderId);
        } else if (status != null) {
            payments = paymentRepository.findByStatus(status);
        } else {
            payments = paymentRepository.findAll();
        }

        return payments.stream()
                .map(paymentMapper::toResponse)
                .toList();
    }

    @Override
    public BigDecimal getCurrentUserTotal(Long userId, Instant from, Instant to) {

        return paymentRepository
                .findByUserIdAndTimestampBetween(userId, from, to)
                .stream()
                .map(Payment::getPaymentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal getAllUsersTotal(Instant from, Instant to) {

        return paymentRepository
                .findByTimestampBetween(from, to)
                .stream()
                .map(Payment::getPaymentAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
