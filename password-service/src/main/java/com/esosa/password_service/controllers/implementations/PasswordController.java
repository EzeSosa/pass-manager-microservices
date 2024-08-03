package com.esosa.password_service.controllers.implementations;

import com.esosa.password_service.controllers.interfaces.IPasswordController;
import com.esosa.password_service.controllers.requests.CreatePasswordRequest;
import com.esosa.password_service.controllers.requests.UpdatePasswordRequest;
import com.esosa.password_service.controllers.responses.PasswordResponse;
import com.esosa.password_service.services.interfaces.IPasswordService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PasswordController implements IPasswordController {
    private final IPasswordService passwordService;

    public PasswordController(IPasswordService passwordService) {
        this.passwordService = passwordService;
    }

    @Override
    public Page<PasswordResponse> getUserPasswords(UUID userId, int size, int pageNumber) {
        return passwordService.getUserPasswords(userId, size, pageNumber);
    }

    @Override
    public PasswordResponse getPassword(UUID passwordId) {
        return passwordService.getPassword(passwordId);
    }

    @Override
    public PasswordResponse savePassword(CreatePasswordRequest createPasswordRequest) {
        return passwordService.savePassword(createPasswordRequest);
    }

    @Override
    public PasswordResponse updatePassword(UUID passwordId, UpdatePasswordRequest updatePasswordRequest) {
        return passwordService.updatePassword(passwordId, updatePasswordRequest);
    }

    @Override
    public void deletePassword(UUID passwordId) {
        passwordService.deletePassword(passwordId);
    }
}