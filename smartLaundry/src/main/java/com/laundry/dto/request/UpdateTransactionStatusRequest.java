package com.laundry.dto.request;

import com.laundry.enums.TransactionStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTransactionStatusRequest {

    @NotNull(message = "Transaction status is required")
    private TransactionStatus status;
}