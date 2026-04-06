package com.washify.api.core.dto;

import java.time.LocalDateTime;

public record BookingRequest(
        Long serviceId,
        LocalDateTime startTime
) {}