package com.washify.api.core.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ServiceRequest(
        @NotBlank(message = "Numele serviciului este obligatoriu")
        String name,

        String description,

        @NotNull(message = "Prețul este obligatoriu")
        @Positive(message = "Prețul trebuie să fie mai mare decât 0")
        Double price,

        @NotNull(message = "Durata este obligatorie")
        @Positive(message = "Durata trebuie să fie exprimată în minute")
        Integer durationMinutes
) {}