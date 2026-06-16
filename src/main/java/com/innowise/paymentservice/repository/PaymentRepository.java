package com.innowise.paymentservice.repository;

import com.innowise.paymentservice.model.entity.Payment;
import com.innowise.paymentservice.model.entity.PaymentStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface PaymentRepository extends MongoRepository<Payment, String> {

    List<Payment> findByUserId(Long userId);

    List<Payment> findByOrderId(Long orderId);

    List<Payment> findByStatus(PaymentStatus status);

    List<Payment> findByUserIdAndTimestampBetween(
            Long userId,
            Instant from,
            Instant to
    );

    List<Payment> findByTimestampBetween(
            Instant from,
            Instant to
    );
}
