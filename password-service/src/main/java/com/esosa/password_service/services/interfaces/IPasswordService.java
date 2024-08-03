package com.esosa.password_service.services.interfaces;

import com.esosa.password_service.controllers.requests.CreatePasswordRequest;
import com.esosa.password_service.controllers.requests.UpdatePasswordRequest;
import com.esosa.password_service.controllers.responses.PasswordResponse;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IPasswordService {
    Page<PasswordResponse> getUserPasswords(UUID userId, int size, int pageNumber);
    PasswordResponse getPassword(UUID passwordId);
    PasswordResponse savePassword(CreatePasswordRequest createPasswordRequest);
    PasswordResponse updatePassword(UUID passwordId, UpdatePasswordRequest updatePasswordRequest);
    void deletePassword(UUID passwordId);
}