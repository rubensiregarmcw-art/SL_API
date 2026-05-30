package com.laundry.controller;

import com.laundry.dto.response.ReportResponse;
import com.laundry.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/daily")
    public ResponseEntity<ReportResponse>
    getDailyReport() {

        return ResponseEntity.ok(
                reportService.getDailyReport()
        );
    }

    @GetMapping("/weekly")
    public ResponseEntity<ReportResponse>
    getWeeklyReport() {

        return ResponseEntity.ok(
                reportService.getWeeklyReport()
        );
    }

    @GetMapping("/monthly")
    public ResponseEntity<ReportResponse>
    getMonthlyReport() {

        return ResponseEntity.ok(
                reportService.getMonthlyReport()
        );
    }
}