package com.laundry.repository;

import com.laundry.entity.LaundryService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LaundryServiceRepository
        extends JpaRepository<LaundryService, Long> {

    List<LaundryService> findByBusinessId(Long businessId);

    Optional<LaundryService> findByIdAndBusinessId(
            Long id,
            Long businessId
    );
}