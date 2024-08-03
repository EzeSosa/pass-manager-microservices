package com.esosa.user_service.client;

import com.esosa.user_service.controllers.responses.PasswordPageResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.UUID;

@HttpExchange
public interface PasswordClient {
    @GetExchange("/api/v1/passwords/users/{userId}")
    public PasswordPageResponse getPasswordsByUserId(
            @PathVariable UUID userId,
            @RequestParam int size,
            @RequestParam int pageNumber
    );
}