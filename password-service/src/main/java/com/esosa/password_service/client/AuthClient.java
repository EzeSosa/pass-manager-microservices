package com.esosa.password_service.client;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange
public interface AuthClient {
    @PostExchange("/auth/validate/{accessToken}")
    Boolean validateToken(@PathVariable String accessToken);
}