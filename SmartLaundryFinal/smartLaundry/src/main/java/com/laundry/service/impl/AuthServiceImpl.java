package com.laundry.service.impl;

import com.laundry.dto.request.LoginRequest;
import com.laundry.dto.request.RegisterRequest;
import com.laundry.dto.response.AuthResponse;
import com.laundry.entity.Business;
import com.laundry.entity.Employee;
import com.laundry.entity.Owner;
import com.laundry.entity.User;
import com.laundry.exception.DuplicateUsernameException;
import com.laundry.exception.UnauthorizedException;
import com.laundry.repository.BusinessRepository;
import com.laundry.repository.UserRepository;
import com.laundry.security.JwtService;
import com.laundry.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BusinessRepository businessRepository;

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public AuthResponse register(
            RegisterRequest request
    ) {

        if (userRepository.existsByUsername(
                request.getOwnerUsername()
        )) {
            throw new DuplicateUsernameException(
                    "Owner username already exists"
            );
        }

        if (userRepository.existsByUsername(
                request.getEmployeeUsername()
        )) {
            throw new DuplicateUsernameException(
                    "Employee username already exists"
            );
        }

        Business business = Business.builder()
                .businessName(
                        request.getBusinessName()
                )
                .build();

        business = businessRepository.save(business);

        Owner owner = new Owner(
                business,
                request.getOwnerUsername(),
                passwordEncoder.encode(
                        request.getOwnerPassword()
                ),
                request.getOwnerFullName()
        );

        Employee employee = new Employee(
                business,
                request.getEmployeeUsername(),
                passwordEncoder.encode(
                        request.getEmployeePassword()
                ),
                request.getEmployeeFullName()
        );

        userRepository.save(owner);
        userRepository.save(employee);

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getOwnerUsername(),
                                request.getOwnerPassword()
                        )
                );

        UserDetails userDetails =
                (UserDetails) authentication.getPrincipal();

        String token =
                jwtService.generateToken(userDetails);

        return AuthResponse.builder()
                .token(token)
                .username(owner.getUsername())
                .role(owner.getRole())
                .businessName(
                        business.getBusinessName()
                )
                .build();
    }

    @Override
    public AuthResponse login(
            LoginRequest request
    ) {

        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    request.getUsername(),
                                    request.getPassword()
                            )
                    );

            UserDetails userDetails =
                    (UserDetails) authentication.getPrincipal();

            String token =
                    jwtService.generateToken(userDetails);

            User user =
                    userRepository
                            .findByUsername(
                                    request.getUsername()
                            )
                            .orElseThrow();

            return AuthResponse.builder()
                    .token(token)
                    .username(
                            user.getUsername()
                    )
                    .role(
                            user.getRole()
                    )
                    .businessName(
                            user.getBusiness()
                                    .getBusinessName()
                    )
                    .build();

        } catch (Exception e) {

            throw new UnauthorizedException(
                    "Invalid username or password"
            );
        }
    }
}