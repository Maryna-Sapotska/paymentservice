package com.innowise.paymentservice.mapper;

import com.innowise.paymentservice.model.dto.CreatePaymentRequest;
import com.innowise.paymentservice.model.dto.PaymentResponse;
import com.innowise.paymentservice.model.entity.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    Payment toEntity(CreatePaymentRequest request);

    PaymentResponse toResponse(Payment payment);
}
