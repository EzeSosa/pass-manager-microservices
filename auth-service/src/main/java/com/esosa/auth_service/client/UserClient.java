package com.esosa.auth_service.client;

import com.esosa.auth_service.controllers.requests.AuthRequest;
import com.esosa.auth_service.controllers.responses.UserResponse;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface UserClient {
    @PostExchange("/api/v1/users/")
    UserResponse saveUser(@RequestBody @Valid AuthRequest authRequest);

    @GetExchange("/api/v1/users/{username}")
    UserResponse getUserByUsername(@PathVariable String username);
}