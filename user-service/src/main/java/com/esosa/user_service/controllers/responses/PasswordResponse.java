package com.esosa.user_service.controllers.responses;

import java.util.UUID;

public record PasswordResponse(
        UUID passwordId,
        String name,
        String password
) {}