package com.esosa.password_service.controllers.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record CreatePasswordRequest(
        @NotNull @Size(min = 1, max = 30, message = "Password name must be between 1 and 30 characters")
        String name,

        @NotNull
        UUID userId
) {}