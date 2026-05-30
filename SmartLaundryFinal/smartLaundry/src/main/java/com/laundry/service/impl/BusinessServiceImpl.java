package com.laundry.service.impl;

import com.laundry.entity.Business;
import com.laundry.entity.User;
import com.laundry.exception.ResourceNotFoundException;
import com.laundry.repository.BusinessRepository;
import com.laundry.repository.UserRepository;
import com.laundry.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl
        implements BusinessService {

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;

    @Override
    public Business getBusinessProfile() {

        return getCurrentUser()
                .getBusiness();
    }

    @Override
    public Business updateBusinessName(
            String businessName
    ) {

        Business business =
                getCurrentUser()
                        .getBusiness();

        business.setBusinessName(
                businessName
        );

        return businessRepository.save(
                business
        );
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username =
                authentication.getName();

        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );
    }
}