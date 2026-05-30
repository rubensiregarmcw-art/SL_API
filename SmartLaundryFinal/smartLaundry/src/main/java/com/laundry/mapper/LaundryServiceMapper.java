package com.laundry.mapper;

import com.laundry.dto.response.LaundryServiceResponse;
import com.laundry.entity.LaundryService;

public class LaundryServiceMapper {

    private LaundryServiceMapper() {
    }

    public static LaundryServiceResponse toResponse(
            LaundryService service
    ) {

        return LaundryServiceResponse.builder()
                .id(service.getId())
                .serviceName(service.getServiceName())
                .serviceType(service.getServiceType())
                .pricePerKg(service.getPricePerKg())
                .estimatedHours(service.getEstimatedHours())
                .build();
    }
}