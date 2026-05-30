package com.laundry.dto.response;

import com.laundry.enums.PaymentStatus;
import com.laundry.enums.TransactionStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class TransactionResponse {

    private Long id;

    private String customerName;

    private String employeeName;

    private BigDecimal totalPrice;

    private TransactionStatus status;

    private PaymentStatus paymentStatus;

    private LocalDate pickupDate;

    private LocalDateTime createdAt;
}