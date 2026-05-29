package com.laundry.repository;

import com.laundry.entity.Transaction;
import com.laundry.enums.TransactionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface TransactionRepository
        extends JpaRepository<Transaction, Long> {

    Page<Transaction> findByBusinessId(
            Long businessId,
            Pageable pageable
    );

    Page<Transaction> findByBusinessIdAndStatus(
            Long businessId,
            TransactionStatus status,
            Pageable pageable
    );

    Page<Transaction> findByBusinessIdAndCreatedAtBetween(
            Long businessId,
            LocalDate startDate,
            LocalDate endDate,
            Pageable pageable
    );

    Page<Transaction> findByBusinessIdAndCustomerNameContainingIgnoreCase(
            Long businessId,
            String customerName,
            Pageable pageable
    );
}