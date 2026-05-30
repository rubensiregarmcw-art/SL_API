package com.laundry.dto.response;

import com.laundry.enums.ServiceType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class LaundryServiceResponse {

    private Long id;

    private String serviceName;

    private ServiceType serviceType;

    private BigDecimal pricePerKg;

    private Integer estimatedHours;
}