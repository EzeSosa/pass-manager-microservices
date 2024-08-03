package com.esosa.auth_service.controllers.responses;

public record AuthResponse(
        UserResponse user,
        String accessToken
) {}