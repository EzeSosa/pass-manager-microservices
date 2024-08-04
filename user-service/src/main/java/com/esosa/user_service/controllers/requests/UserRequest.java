package com.esosa.user_service.controllers.requests;

import jakarta.validation.constraints.NotNull;

public record UserRequest(
        @NotNull String username,
        @NotNull String password
) {}