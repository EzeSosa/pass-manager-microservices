package com.esosa.password_service.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import java.util.UUID;

@HttpExchange
public interface UserClient {
    @GetExchange("/api/v1/users/{userId}/exists")
    Boolean existsUserById(@PathVariable UUID userId);
}