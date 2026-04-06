package com.washify.api.core.service;

import com.washify.api.core.domain.Car;
import com.washify.api.core.domain.User;
import com.washify.api.core.dto.RegisterRequest;
import com.washify.api.infrastructure.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Email-ul este deja înregistrat!");
        }


        User user = new User();
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setFirstName(request.firstName());
        user.setLastName(request.lastName());
        user.setPhoneNumber(request.phoneNumber());


        user.setCars(new ArrayList<>());


        if ("USER".equalsIgnoreCase(request.role()) && request.cars() != null) {
            for (RegisterRequest.CarRequest carDto : request.cars()) {
                Car car = Car.builder()
                        .licensePlate(carDto.licensePlate())
                        .carType(carDto.carType())
                        .user(user)
                        .build();

                user.getCars().add(car);
            }
        }


        userRepository.save(user);
    }
}