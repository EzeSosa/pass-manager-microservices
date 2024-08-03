package com.esosa.user_service.controllers.responses;

import java.util.List;

public record PasswordPageResponse(
        List<PasswordResponse> content,
        int number,
        int size,
        long totalElements,
        int totalPages
) {}