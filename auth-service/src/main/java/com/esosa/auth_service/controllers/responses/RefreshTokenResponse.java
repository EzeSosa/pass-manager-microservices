package com.esosa.auth_service.controllers.responses;

public record RefreshTokenResponse(
        String newAccessToken
) {}