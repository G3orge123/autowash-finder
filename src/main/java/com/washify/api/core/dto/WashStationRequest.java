package com.washify.api.core.dto;

public record WashStationRequest(
        String name,
        String address,
        Double latitude,
        Double longitude,
        Long ownerId
) {}