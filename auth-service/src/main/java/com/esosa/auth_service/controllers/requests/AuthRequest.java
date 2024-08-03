package com.esosa.auth_service.controllers.requests;

import jakarta.validation.constraints.NotNull;

public record AuthRequest(
        @NotNull String username,
        @NotNull String password
) {}