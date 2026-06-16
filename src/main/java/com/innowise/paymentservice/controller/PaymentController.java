package com.innowise.paymentservice.controller;

import com.innowise.paymentservice.model.dto.CreatePaymentRequest;
import com.innowise.paymentservice.model.dto.PaymentResponse;
import com.innowise.paymentservice.model.entity.PaymentStatus;
import com.innowise.paymentservice.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping(PaymentController.REST_URL)
@RequiredArgsConstructor
@Tag(name = "Payments", description = "Payment management API")
public class PaymentController {

    public static final String REST_URL = "/payments";

    private final PaymentService paymentService;

    @Operation(summary = "Create payment")
    @ApiResponse(responseCode = "201", description = "Payment created")
    @PostMapping
    public ResponseEntity<PaymentResponse> create(@Valid @RequestBody CreatePaymentRequest dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(paymentService.create(dto));
    }

    @Operation(summary = "Get all orders with filters and pagination")
    @ApiResponse(responseCode = "200", description = "Orders retrieved")
    @GetMapping
    public ResponseEntity<List<PaymentResponse>> getPayments(
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) Long orderId,
            @RequestParam(required = false) PaymentStatus status) {

        return ResponseEntity.ok(
                paymentService.getPayments(userId, orderId, status)
        );
    }

    @GetMapping("/me/total")
    public ResponseEntity<BigDecimal> getCurrentUserTotal(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant from,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant to) {

        Long currentUserId = getCurrentUserId();

        return ResponseEntity.ok(
                paymentService.getCurrentUserTotal(
                        currentUserId,
                        from,
                        to
                )
        );
    }

    @GetMapping("/admin/total")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BigDecimal> getAllUsersTotal(
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant from,

            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant to) {

        return ResponseEntity.ok(
                paymentService.getAllUsersTotal(from, to)
        );
    }

    private Long getCurrentUserId() {
        // TODO получить userId из JWT
        return 1L;
    }
}
