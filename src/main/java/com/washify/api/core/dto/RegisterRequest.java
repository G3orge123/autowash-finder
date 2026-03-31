package com.washify.api.core.dto;

import java.util.List;

public record RegisterRequest(
        String email,
        String password,
        String firstName,
        String lastName,
        String phoneNumber,
        String role,
        List<CarRequest> cars
) {
    public record CarRequest(String licensePlate, String carType) {}
}