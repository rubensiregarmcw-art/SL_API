package com.laundry.dto.request;

import com.laundry.enums.ServiceType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class LaundryServiceRequest {

    @NotBlank(message = "Service name is required")
    private String serviceName;

    @NotNull(message = "Service type is required")
    private ServiceType serviceType;

    @NotNull(message = "Price per kg is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal pricePerKg;

    @NotNull(message = "Estimated hours is required")
    @Positive(message = "Estimated hours must be positive")
    private Integer estimatedHours;
}