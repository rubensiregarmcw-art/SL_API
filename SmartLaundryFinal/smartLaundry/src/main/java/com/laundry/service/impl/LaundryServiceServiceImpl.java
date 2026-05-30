package com.laundry.service.impl;

import com.laundry.dto.request.LaundryServiceRequest;
import com.laundry.dto.response.LaundryServiceResponse;
import com.laundry.entity.Business;
import com.laundry.entity.LaundryService;
import com.laundry.entity.User;
import com.laundry.mapper.LaundryServiceMapper;
import com.laundry.repository.LaundryServiceRepository;
import com.laundry.repository.UserRepository;
import com.laundry.service.LaundryServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaundryServiceServiceImpl
        implements LaundryServiceService {

    private final LaundryServiceRepository laundryServiceRepository;
    private final UserRepository userRepository;

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username = authentication.getName();

        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException("User not found")
                );
    }

    @Override
    public List<LaundryServiceResponse> getAllServices() {

        User user = getCurrentUser();

        return laundryServiceRepository
                .findByBusinessId(
                        user.getBusiness().getId()
                )
                .stream()
                .map(LaundryServiceMapper::toResponse)
                .toList();
    }

    @Override
    public LaundryServiceResponse getServiceById(
            Long id
    ) {

        User user = getCurrentUser();

        LaundryService service =
                laundryServiceRepository
                        .findByIdAndBusinessId(
                                id,
                                user.getBusiness().getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Service not found"
                                )
                        );

        return LaundryServiceMapper.toResponse(service);
    }

    @Override
    public LaundryServiceResponse createService(
            LaundryServiceRequest request
    ) {

        User user = getCurrentUser();

        Business business = user.getBusiness();

        LaundryService service =
                LaundryService.builder()
                        .business(business)
                        .serviceName(request.getServiceName())
                        .serviceType(request.getServiceType())
                        .pricePerKg(request.getPricePerKg())
                        .estimatedHours(
                                request.getEstimatedHours()
                        )
                        .build();

        service =
                laundryServiceRepository.save(service);

        return LaundryServiceMapper.toResponse(service);
    }

    @Override
    public LaundryServiceResponse updateService(
            Long id,
            LaundryServiceRequest request
    ) {

        User user = getCurrentUser();

        LaundryService service =
                laundryServiceRepository
                        .findByIdAndBusinessId(
                                id,
                                user.getBusiness().getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Service not found"
                                )
                        );

        service.setServiceName(
                request.getServiceName()
        );

        service.setServiceType(
                request.getServiceType()
        );

        service.setPricePerKg(
                request.getPricePerKg()
        );

        service.setEstimatedHours(
                request.getEstimatedHours()
        );

        service =
                laundryServiceRepository.save(service);

        return LaundryServiceMapper.toResponse(service);
    }

    @Override
    public void deleteService(
            Long id
    ) {

        User user = getCurrentUser();

        LaundryService service =
                laundryServiceRepository
                        .findByIdAndBusinessId(
                                id,
                                user.getBusiness().getId()
                        )
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Service not found"
                                )
                        );

        laundryServiceRepository.delete(service);
    }
}