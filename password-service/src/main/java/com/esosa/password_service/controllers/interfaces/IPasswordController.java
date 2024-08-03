package com.esosa.password_service.controllers.interfaces;

import com.esosa.password_service.controllers.requests.CreatePasswordRequest;
import com.esosa.password_service.controllers.requests.UpdatePasswordRequest;
import com.esosa.password_service.controllers.responses.PasswordResponse;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequestMapping("/api/v1/passwords")
public interface IPasswordController {

    @GetMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.OK)
    Page<PasswordResponse> getUserPasswords(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "0") int pageNumber
    );

    @GetMapping("/{passwordId}")
    @ResponseStatus(HttpStatus.OK)
    PasswordResponse getPassword(@PathVariable UUID passwordId);

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    PasswordResponse savePassword(@RequestBody CreatePasswordRequest createPasswordRequest);

    @PatchMapping("/{passwordId}")
    @ResponseStatus(HttpStatus.OK)
    PasswordResponse updatePassword(
            @PathVariable UUID passwordId,
            @RequestBody UpdatePasswordRequest updatePasswordRequest
    );

    @DeleteMapping("/{passwordId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePassword(@PathVariable UUID passwordId);
}