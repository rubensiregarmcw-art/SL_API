package com.laundry.controller;

import com.laundry.entity.Business;
import com.laundry.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/business")
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessService businessService;

    @GetMapping
    public ResponseEntity<Business> getProfile() {

        return ResponseEntity.ok(
                businessService.getBusinessProfile()
        );
    }

    @PutMapping
    public ResponseEntity<Business> updateBusiness(
            @RequestParam String businessName
    ) {

        return ResponseEntity.ok(
                businessService.updateBusinessName(
                        businessName
                )
        );
    }
}