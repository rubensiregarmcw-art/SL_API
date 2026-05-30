package com.laundry.repository;

import com.laundry.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository
        extends JpaRepository<Customer, Long> {

    Page<Customer> findByBusinessId(
            Long businessId,
            Pageable pageable
    );

    Page<Customer> findByBusinessIdAndNameContainingIgnoreCase(
            Long businessId,
            String name,
            Pageable pageable
    );

    Optional<Customer> findByIdAndBusinessId(
            Long id,
            Long businessId
    );
}