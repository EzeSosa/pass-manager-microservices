package com.esosa.user_service.controllers.interfaces;

import com.esosa.user_service.controllers.requests.UserRequest;
import com.esosa.user_service.controllers.responses.PasswordResponse;
import com.esosa.user_service.controllers.responses.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.UUID;

@RequestMapping("/api/v1/users")
public interface IUserController {
    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    UserResponse saveUser(@RequestBody UserRequest userRequest);

    @GetMapping("/{userId}/passwords")
    @ResponseStatus(HttpStatus.OK)
    Page<PasswordResponse> getUserPasswords(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int pageNumber
    );

    @GetMapping("/{userId}/exists")
    @ResponseStatus(HttpStatus.OK)
    Boolean existsUserById(@PathVariable UUID userId);
}