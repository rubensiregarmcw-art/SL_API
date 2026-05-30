package com.laundry.service.impl;

import com.laundry.dto.response.ReportResponse;
import com.laundry.entity.Transaction;
import com.laundry.entity.User;
import com.laundry.enums.TransactionStatus;
import com.laundry.exception.ResourceNotFoundException;
import com.laundry.repository.TransactionRepository;
import com.laundry.repository.UserRepository;
import com.laundry.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl
        implements ReportService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    @Override
    public ReportResponse getDailyReport() {

        LocalDateTime start =
                LocalDateTime.now()
                        .toLocalDate()
                        .atStartOfDay();

        LocalDateTime end =
                start.plusDays(1);

        return generateReport(start, end);
    }

    @Override
    public ReportResponse getWeeklyReport() {

        LocalDateTime start =
                LocalDateTime.now()
                        .minusDays(7);

        LocalDateTime end =
                LocalDateTime.now();

        return generateReport(start, end);
    }

    @Override
    public ReportResponse getMonthlyReport() {

        LocalDateTime start =
                LocalDateTime.now()
                        .minusMonths(1);

        LocalDateTime end =
                LocalDateTime.now();

        return generateReport(start, end);
    }

    private ReportResponse generateReport(
            LocalDateTime start,
            LocalDateTime end
    ) {

        Long businessId =
                getCurrentUser()
                        .getBusiness()
                        .getId();

        List<Transaction> transactions =
                transactionRepository
                        .findByBusinessIdAndCreatedAtBetween(
                                businessId,
                                start,
                                end
                        );

        long totalTransactions =
                transactions.size();

        BigDecimal totalRevenue =
                transactions.stream()
                        .map(Transaction::getTotalPrice)
                        .reduce(
                                BigDecimal.ZERO,
                                BigDecimal::add
                        );

        long completedTransactions =
                transactions.stream()
                        .filter(t ->
                                t.getStatus()
                                        == TransactionStatus.COMPLETED
                        )
                        .count();

        long pendingTransactions =
                transactions.stream()
                        .filter(t ->
                                t.getStatus()
                                        == TransactionStatus.PENDING
                        )
                        .count();

        return ReportResponse.builder()
                .totalTransactions(
                        totalTransactions
                )
                .totalRevenue(
                        totalRevenue
                )
                .completedTransactions(
                        completedTransactions
                )
                .pendingTransactions(
                        pendingTransactions
                )
                .build();
    }

    private User getCurrentUser() {

        Authentication authentication =
                SecurityContextHolder
                        .getContext()
                        .getAuthentication();

        String username =
                authentication.getName();

        return userRepository
                .findByUsername(username)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found"
                        )
                );
    }
}