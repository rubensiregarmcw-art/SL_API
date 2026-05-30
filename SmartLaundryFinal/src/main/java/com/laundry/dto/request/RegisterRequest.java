package com.laundry.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {

    @NotBlank(message = "Business name is required")
    private String businessName;

    @NotBlank(message = "Owner username is required")
    private String ownerUsername;

    @NotBlank(message = "Owner password is required")
    private String ownerPassword;

    @NotBlank(message = "Owner full name is required")
    private String ownerFullName;

    @NotBlank(message = "Employee username is required")
    private String employeeUsername;

    @NotBlank(message = "Employee password is required")
    private String employeePassword;

    @NotBlank(message = "Employee full name is required")
    private String employeeFullName;
}