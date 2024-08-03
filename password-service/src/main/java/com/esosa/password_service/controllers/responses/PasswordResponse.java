package com.esosa.password_service.controllers.responses;

import java.util.UUID;

public record PasswordResponse(
        UUID passwordId,
        String name,
        String password
) {}