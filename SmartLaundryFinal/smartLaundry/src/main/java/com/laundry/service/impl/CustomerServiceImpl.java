package com.laundry.service.impl;

import com.laundry.dto.request.CustomerRequest;
import com.laundry.dto.response.CustomerResponse;
import com.laundry.entity.Business;
import com.laundry.entity.Customer;
import com.laundry.entity.User;
import com.laundry.exception.ResourceNotFoundException;
import com.laundry.mapper.CustomerMapper;
import com.laundry.repository.CustomerRepository;
import com.laundry.repository.UserRepository;
import com.laundry.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl
        implements CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    @Override
    public List<CustomerResponse> getAllCustomers() {

        Business business =
                getCurrentUser().getBusiness();

        return customerRepository.findAll()
                .stream()
                .filter(customer ->
                        customer.getBusiness()
                                .getId()
                                .equals(business.getId())
                )
                .map(CustomerMapper::toResponse)
                .toList();
    }

    @Override
    public CustomerResponse getCustomerById(
            Long id
    ) {

        Customer customer =
                findCustomerAndValidateOwnership(id);

        return CustomerMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse createCustomer(
            CustomerRequest request
    ) {

        Customer customer =
                Customer.builder()
                        .business(
                                getCurrentUser()
                                        .getBusiness()
                        )
                        .name(request.getName())
                        .phone(request.getPhone())
                        .address(request.getAddress())
                        .build();

        customer =
                customerRepository.save(customer);

        return CustomerMapper.toResponse(customer);
    }

    @Override
    public CustomerResponse updateCustomer(
            Long id,
            CustomerRequest request
    ) {

        Customer customer =
                findCustomerAndValidateOwnership(id);

        customer.setName(
                request.getName()
        );

        customer.setPhone(
                request.getPhone()
        );

        customer.setAddress(
                request.getAddress()
        );

        customer =
                customerRepository.save(customer);

        return CustomerMapper.toResponse(customer);
    }

    @Override
    public void deleteCustomer(
            Long id
    ) {

        Customer customer =
                findCustomerAndValidateOwnership(id);

        customerRepository.delete(customer);
    }

    private Customer findCustomerAndValidateOwnership(
            Long customerId
    ) {

        Customer customer =
                customerRepository.findById(customerId)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Customer not found"
                                )
                        );

        Long currentBusinessId =
                getCurrentUser()
                        .getBusiness()
                        .getId();

        if (!customer.getBusiness()
                .getId()
                .equals(currentBusinessId)) {

            throw new ResourceNotFoundException(
                    "Customer not found"
            );
        }

        return customer;
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username =
                authentication.getName();

        System.out.println(
                "CURRENT USER = " + username
        );

        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );
    }
}