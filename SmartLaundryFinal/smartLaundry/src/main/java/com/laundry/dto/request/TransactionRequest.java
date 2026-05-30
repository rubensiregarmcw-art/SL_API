package com.laundry.dto.request;

import com.laundry.enums.PaymentStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
public class TransactionRequest {

    @NotNull(message = "Customer ID is required")
    private Long customerId;

    @NotNull(message = "Payment status is required")
    private PaymentStatus paymentStatus;

    private LocalDate pickupDate;

    @NotEmpty(message = "Transaction details cannot be empty")
    private List<TransactionDetailRequest> details;
}