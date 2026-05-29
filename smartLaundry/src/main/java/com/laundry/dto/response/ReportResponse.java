package com.laundry.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class ReportResponse {

    private Long totalTransactions;

    private BigDecimal totalRevenue;

    private Long completedTransactions;

    private Long pendingTransactions;
}