package com.esosa.auth_service.controllers.requests;

import jakarta.validation.constraints.NotNull;

public record RefreshTokenRequest(
        @NotNull String refreshToken
) {}