package com.esosa.auth_service.controllers.requests;

import jakarta.validation.constraints.NotNull;

public record ValidateTokenRequest(
        @NotNull String username,
        @NotNull String accessToken
) {}