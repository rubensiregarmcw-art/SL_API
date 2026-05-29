package com.laundry.repository;

import com.laundry.entity.TransactionDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionDetailRepository
        extends JpaRepository<TransactionDetail, Long> {
}