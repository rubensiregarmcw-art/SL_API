package com.laundry.service;

import com.laundry.dto.response.ReportResponse;

public interface ReportService {

    ReportResponse getDailyReport();

    ReportResponse getWeeklyReport();

    ReportResponse getMonthlyReport();
}