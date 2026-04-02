package com.washify.api.core.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record ReviewRequest(
        @Min(1) @Max(5) Integer rating,
        @NotBlank String comment,
        Long washStationId
) {}