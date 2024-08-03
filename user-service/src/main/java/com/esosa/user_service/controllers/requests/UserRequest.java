package com.esosa.user_service.controllers.requests;

import org.springframework.lang.NonNull;

public record UserRequest(
        @NonNull String username,
        @NonNull String password
) {}