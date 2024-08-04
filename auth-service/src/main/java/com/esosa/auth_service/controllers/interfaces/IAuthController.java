package com.esosa.auth_service.controllers.interfaces;

import com.esosa.auth_service.controllers.requests.AuthRequest;
import com.esosa.auth_service.controllers.responses.AuthResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

@RequestMapping("/auth")
public interface IAuthController {

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    void register(@RequestBody @Valid AuthRequest authRequest);

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    AuthResponse login(@RequestBody @Valid AuthRequest authRequest);

    @PostMapping("/validate/{accessToken}")
    @ResponseStatus(HttpStatus.OK)
    Boolean validateToken(@PathVariable String accessToken);
}