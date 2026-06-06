package com.laundry.mapper;
import com.laundry.dto.response.TransactionResponse;
import com.laundry.entity.Transaction;
import com.laundry.entity.TransactionDetail;

public class TransactionMapper {
    private TransactionMapper() {}

    public static TransactionResponse toResponse(Transaction transaction) {
        TransactionDetail firstDetail = 
            (transaction.getTransactionDetails() != null 
                && !transaction.getTransactionDetails().isEmpty())
            ? transaction.getTransactionDetails().get(0)
            : null;

        return TransactionResponse.builder()
                .id(transaction.getId())
                .customerName(transaction.getCustomer().getName())
                .employeeName(transaction.getEmployee().getFullName())
                .totalPrice(transaction.getTotalPrice())
                .status(transaction.getStatus())
                .paymentStatus(transaction.getPaymentStatus())
                .pickupDate(transaction.getPickupDate())
                .createdAt(transaction.getCreatedAt())
                .serviceName(firstDetail != null
                    ? firstDetail.getService().getServiceName()
                    : null)
                .weight(firstDetail != null
                    ? firstDetail.getWeight()
                    : null)
                .build();
    }
}