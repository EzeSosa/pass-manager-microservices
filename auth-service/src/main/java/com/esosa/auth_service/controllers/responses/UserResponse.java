package com.esosa.auth_service.controllers.responses;

import java.util.UUID;

public record UserResponse(
        UUID userId,
        String username,
        String password
) {}