package com.laundry.service;

import com.laundry.dto.request.TransactionRequest;
import com.laundry.dto.request.UpdateTransactionStatusRequest;
import com.laundry.dto.response.TransactionResponse;
import org.springframework.data.domain.Page;

public interface TransactionService {

    Page<TransactionResponse> getAllTransactions(
            String status,
            String customer,
            String date,
            int page,
            int size
    );

    TransactionResponse getTransactionById(Long id);

    TransactionResponse createTransaction(
            TransactionRequest request
    );

    TransactionResponse updateTransaction(
            Long id,
            TransactionRequest request
    );

    TransactionResponse updateStatus(
            Long id,
            UpdateTransactionStatusRequest request
    );

    void cancelTransaction(Long id);

    void deleteTransaction(Long id);
}