package com.laundry.mapper;

import com.laundry.dto.response.TransactionResponse;
import com.laundry.entity.Transaction;
import com.laundry.entity.TransactionDetail;
import com.laundry.entity.LaundryService;

public class TransactionMapper {

    private TransactionMapper() {
    }

    public static TransactionResponse toResponse(
            Transaction transaction
    ) {

        return TransactionResponse.builder()
                .id(transaction.getId())
                .customerName(
                        transaction.getCustomer().getName()
                )
                .employeeName(
                        transaction.getEmployee().getFullName()
                )
                .serviceName(getServiceName())
                .weight(getWeight())
                .totalPrice(transaction.getTotalPrice())
                .status(transaction.getStatus())
                .paymentStatus(transaction.getPaymentStatus())
                .pickupDate(transaction.getPickupDate())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
}
