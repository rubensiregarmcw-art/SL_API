package com.laundry.controller;

import com.laundry.dto.request.TransactionRequest;
import com.laundry.dto.request.UpdateTransactionStatusRequest;
import com.laundry.dto.request.UpdatePaymentStatusRequest;
import com.laundry.dto.response.TransactionResponse;
import com.laundry.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping
    public ResponseEntity<Page<TransactionResponse>>
    getAllTransactions(

            @RequestParam(required = false)
            String status,

            @RequestParam(required = false)
            String customer,

            @RequestParam(required = false)
            String date,

            @RequestParam(defaultValue = "0")
            int page,

            @RequestParam(defaultValue = "10")
            int size
    ) {

        return ResponseEntity.ok(
                transactionService.getAllTransactions(
                        status,
                        customer,
                        date,
                        page,
                        size
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<TransactionResponse>
    getTransactionById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                transactionService.getTransactionById(id)
        );
    }

    @PostMapping
    public ResponseEntity<TransactionResponse>
    createTransaction(

            @Valid
            @RequestBody
            TransactionRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        transactionService.createTransaction(
                                request
                        )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionResponse>
    updateTransaction(

            @PathVariable Long id,

            @Valid
            @RequestBody
            TransactionRequest request
    ) {

        return ResponseEntity.ok(
                transactionService.updateTransaction(
                        id,
                        request
                )
        );
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<TransactionResponse>
    updateStatus(

            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateTransactionStatusRequest request
    ) {

        return ResponseEntity.ok(
                transactionService.updateStatus(
                        id,
                        request
                )
        );
    }

    @PatchMapping("/{id}/payment-status")
    public ResponseEntity<TransactionResponse> updatePaymentStatus(
            @PathVariable Long id,
            @RequestBody @Valid UpdatePaymentStatusRequest request
    ) {
        return ResponseEntity.ok(
                transactionService.updatePaymentStatus(id, request)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteTransaction(
            @PathVariable Long id
    ) {

        transactionService.deleteTransaction(id);

        return ResponseEntity.noContent()
                .build();
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void>
    cancelTransaction(
            @PathVariable Long id
    ) {

        transactionService.cancelTransaction(id);

        return ResponseEntity.noContent()
                .build();
    }
}
