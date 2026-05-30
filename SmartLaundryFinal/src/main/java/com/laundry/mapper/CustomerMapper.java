package com.laundry.mapper;

import com.laundry.dto.response.CustomerResponse;
import com.laundry.entity.Customer;

public class CustomerMapper {

    private CustomerMapper() {
    }

    public static CustomerResponse toResponse(Customer customer) {

        return CustomerResponse.builder()
                .id(customer.getId())
                .name(customer.getName())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .build();
    }
}