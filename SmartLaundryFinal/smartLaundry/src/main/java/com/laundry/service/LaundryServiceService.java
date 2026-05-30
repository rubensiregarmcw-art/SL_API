package com.laundry.service;

import com.laundry.dto.request.LaundryServiceRequest;
import com.laundry.dto.response.LaundryServiceResponse;

import java.util.List;

public interface LaundryServiceService {

    List<LaundryServiceResponse> getAllServices();

    LaundryServiceResponse getServiceById(Long id);

    LaundryServiceResponse createService(
            LaundryServiceRequest request
    );

    LaundryServiceResponse updateService(
            Long id,
            LaundryServiceRequest request
    );

    void deleteService(Long id);
}