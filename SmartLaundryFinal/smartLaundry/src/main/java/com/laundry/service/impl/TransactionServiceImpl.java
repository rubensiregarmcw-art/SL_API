package com.laundry.service.impl;

import com.laundry.enums.TransactionStatus;
import com.laundry.dto.request.TransactionRequest;
import com.laundry.dto.request.TransactionDetailRequest;
import com.laundry.dto.request.UpdateTransactionStatusRequest;
import com.laundry.dto.request.UpdatePaymentStatusRequest;
import com.laundry.dto.response.TransactionResponse;
import com.laundry.entity.Transaction;
import com.laundry.entity.User;
import com.laundry.entity.Customer;
import com.laundry.entity.Employee;
import com.laundry.entity.LaundryService;
import com.laundry.entity.TransactionDetail;
import com.laundry.exception.ResourceNotFoundException;
import com.laundry.mapper.TransactionMapper;
import com.laundry.repository.TransactionRepository;
import com.laundry.repository.UserRepository;
import com.laundry.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import com.laundry.repository.CustomerRepository;
import com.laundry.repository.LaundryServiceRepository;
import com.laundry.repository.TransactionDetailRepository;

import java.util.List;
import java.math.BigDecimal;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl
        implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionDetailRepository transactionDetailRepository;
    private final CustomerRepository customerRepository;
    private final LaundryServiceRepository laundryServiceRepository;
    private final UserRepository userRepository;

    @Override
    public Page<TransactionResponse> getAllTransactions(
            String status,
            String customer,
            String date,
            int page,
            int size
    ) {

        Long businessId =
                getCurrentUser()
                        .getBusiness()
                        .getId();

        List<TransactionResponse> responses =
                transactionRepository.findAll()
                        .stream()
                        .filter(transaction ->
                                transaction.getBusiness()
                                        .getId()
                                        .equals(businessId)
                        )
                        .map(TransactionMapper::toResponse)
                        .toList();

        return new PageImpl<>(responses);
    }

    @Override
    public TransactionResponse getTransactionById(
            Long id
    ) {

        Transaction transaction =
                findTransactionAndValidateOwnership(id);

        return TransactionMapper.toResponse(
                transaction
        );
    }

    @Override
    public TransactionResponse createTransaction(
            TransactionRequest request
    ) {

        User currentUser = getCurrentUser();

        Customer customer =
                customerRepository.findById(
                        request.getCustomerId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found"
                        )
                );

        if (!customer.getBusiness()
                .getId()
                .equals(
                        currentUser.getBusiness()
                                .getId()
                )) {

            throw new ResourceNotFoundException(
                    "Customer not found"
            );
        }

        Transaction transaction =
                Transaction.builder()
                        .business(
                                currentUser.getBusiness()
                        )
                        .customer(customer)
                        .employee(
                                (Employee) currentUser
                        )
                        .status(
                                TransactionStatus.PENDING
                        )
                        .paymentStatus(
                                request.getPaymentStatus()
                        )
                        .pickupDate(
                                request.getPickupDate()
                        )
                        .totalPrice(BigDecimal.ZERO)
                        .build();

        transaction =
                transactionRepository.save(
                        transaction
                );

        BigDecimal totalPrice =
                BigDecimal.ZERO;

        List<TransactionDetail> details =
                new ArrayList<>();

        for (TransactionDetailRequest detailRequest
                : request.getDetails()) {

            LaundryService service =
                    laundryServiceRepository
                            .findByIdAndBusinessId(
                                    detailRequest.getServiceId(),
                                    currentUser
                                            .getBusiness()
                                            .getId()
                            )
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Service not found"
                                    )
                            );

            BigDecimal subtotal =
                    service.getPricePerKg()
                            .multiply(
                                    BigDecimal.valueOf(
                                            detailRequest.getWeight()
                                    )
                            );

            TransactionDetail detail =
                    TransactionDetail.builder()
                            .transaction(transaction)
                            .service(service)
                            .weight(
                                    detailRequest.getWeight()
                            )
                            .subtotal(subtotal)
                            .build();

            details.add(detail);

            totalPrice =
                    totalPrice.add(subtotal);
        }

        transactionDetailRepository
                .saveAll(details);

        transaction.setTransactionDetails(
                details
        );

        transaction.setTotalPrice(
                totalPrice
        );

        transaction =
                transactionRepository.save(
                        transaction
                );

        return TransactionMapper.toResponse(
                transaction
        );
    }

    @Override
    public TransactionResponse updateTransaction(
            Long id,
            TransactionRequest request
    ) {

        Transaction transaction =
                findTransactionAndValidateOwnership(id);

        User currentUser =
                getCurrentUser();

        Customer customer =
                customerRepository.findById(
                        request.getCustomerId()
                ).orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found"
                        )
                );

        transaction.setCustomer(customer);
        transaction.setPaymentStatus(
                request.getPaymentStatus()
        );
        transaction.setPickupDate(
                request.getPickupDate()
        );

        transaction.getTransactionDetails()
                .clear();

        BigDecimal totalPrice =
                BigDecimal.ZERO;

        List<TransactionDetail> details =
                new ArrayList<>();

        for (TransactionDetailRequest detailRequest
                : request.getDetails()) {

            LaundryService service =
                    laundryServiceRepository
                            .findByIdAndBusinessId(
                                    detailRequest.getServiceId(),
                                    currentUser
                                            .getBusiness()
                                            .getId()
                            )
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Service not found"
                                    )
                            );

            BigDecimal subtotal =
                    service.getPricePerKg()
                            .multiply(
                                    BigDecimal.valueOf(
                                            detailRequest.getWeight()
                                    )
                            );

            TransactionDetail detail =
                    TransactionDetail.builder()
                            .transaction(transaction)
                            .service(service)
                            .weight(
                                    detailRequest.getWeight()
                            )
                            .subtotal(subtotal)
                            .build();

            details.add(detail);

            totalPrice =
                    totalPrice.add(subtotal);
        }

        transaction.setTransactionDetails(
                details
        );

        transaction.setTotalPrice(
                totalPrice
        );

        transaction =
                transactionRepository.save(
                        transaction
                );

        return TransactionMapper.toResponse(
                transaction
        );
    }

    @Override
    public TransactionResponse updateStatus(
            Long id,
            UpdateTransactionStatusRequest request
    ) {

        Transaction transaction =
                findTransactionAndValidateOwnership(id);

        transaction.setStatus(
                request.getStatus()
        );

        transaction =
                transactionRepository.save(
                        transaction
                );

        return TransactionMapper.toResponse(
                transaction
        );
    }

    @Override
    public TransactionResponse updatePaymentStatus(
            Long id,
            UpdatePaymentStatusRequest request
    ) {
        Transaction transaction =
               findTransactionAndValidateOwnership(id);
            
        transaction.setPaymentStatus(
               request.getPaymentStatus()
        );
        transaction =
               transactionRepository.save(
                      transaction
               );
         return TransactionMapper.toResponse(
                 transaction
         );
     }            

    @Override
    public void cancelTransaction(
            Long id
    ) {

        Transaction transaction =
                findTransactionAndValidateOwnership(id);

        transaction.setStatus(
                com.laundry.enums.TransactionStatus.CANCELLED
        );

        transactionRepository.save(
                transaction
        );
    }

    @Override
    public void deleteTransaction(
            Long id
    ) {

        Transaction transaction =
                findTransactionAndValidateOwnership(id);

        transactionRepository.delete(
                transaction
        );
    }

    private Transaction findTransactionAndValidateOwnership(
            Long transactionId
    ) {

        Long businessId =
                getCurrentUser()
                        .getBusiness()
                        .getId();

        return transactionRepository
                .findByIdAndBusinessId(
                        transactionId,
                        businessId
                )
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Transaction not found"
                        )
                );
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
