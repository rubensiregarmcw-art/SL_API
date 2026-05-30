package com.laundry.controller;

import com.laundry.dto.request.LaundryServiceRequest;
import com.laundry.dto.response.LaundryServiceResponse;
import com.laundry.service.LaundryServiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class LaundryServiceController {

    private final LaundryServiceService laundryServiceService;

    @GetMapping
    public ResponseEntity<List<LaundryServiceResponse>>
    getAllServices() {

        return ResponseEntity.ok(
                laundryServiceService.getAllServices()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaundryServiceResponse>
    getServiceById(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                laundryServiceService.getServiceById(id)
        );
    }

    @PostMapping
    public ResponseEntity<LaundryServiceResponse>
    createService(
            @Valid
            @RequestBody
            LaundryServiceRequest request
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        laundryServiceService.createService(
                                request
                        )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<LaundryServiceResponse>
    updateService(
            @PathVariable Long id,
            @Valid
            @RequestBody
            LaundryServiceRequest request
    ) {

        return ResponseEntity.ok(
                laundryServiceService.updateService(
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>
    deleteService(
            @PathVariable Long id
    ) {

        laundryServiceService.deleteService(id);

        return ResponseEntity.noContent()
                .build();
    }
}