package com.esosa.password_service.services.implementations;

import com.esosa.password_service.client.UserClient;
import com.esosa.password_service.controllers.requests.CreatePasswordRequest;
import com.esosa.password_service.controllers.requests.UpdatePasswordRequest;
import com.esosa.password_service.controllers.responses.PasswordResponse;
import com.esosa.password_service.data.model.Password;
import com.esosa.password_service.data.repositories.IPasswordRepository;
import com.esosa.password_service.services.interfaces.IPasswordService;
import com.esosa.password_service.services.mapper.PasswordMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class PasswordService implements IPasswordService {
    private final IPasswordRepository passwordRepository;
    private final UserClient userClient;

    public PasswordService(IPasswordRepository passwordRepository, UserClient userClient) {
        this.passwordRepository = passwordRepository;
        this.userClient = userClient;
    }

    @Override
    public Page<PasswordResponse> getUserPasswords(UUID userId, int size, int pageNumber) {
        PageRequest pageRequest = PageRequest.of(pageNumber, size, Sort.by("name"));
        return passwordRepository.findByUserId(pageRequest, userId)
                .map(PasswordMapper::buildPasswordResponse);
    }

    @Override
    public PasswordResponse getPassword(UUID passwordId) {
        Password existentPassword = findPasswordByIdOrThrowException(passwordId);
        return PasswordMapper.buildPasswordResponse(existentPassword);
    }

    @Override
    public PasswordResponse savePassword(CreatePasswordRequest createPasswordRequest) {
        ifUserNotExistsThrowException(createPasswordRequest.userId());
        ifExistsPasswordNameForUserThrowException(createPasswordRequest.userId(), createPasswordRequest.name());

        String password = generatePassword();
        Password newPassword = PasswordMapper.buildPassword(createPasswordRequest, password);
        passwordRepository.save(newPassword);

        return PasswordMapper.buildPasswordResponse(newPassword);
    }

    @Override
    public PasswordResponse updatePassword(UUID passwordId, UpdatePasswordRequest updatePasswordRequest) {
        Password existentPassword = findPasswordByIdOrThrowException(passwordId);
        if (!existentPassword.getName().equalsIgnoreCase(updatePasswordRequest.name()))
            ifExistsPasswordNameForUserThrowException(existentPassword.getUserId(), updatePasswordRequest.name());

        existentPassword.setName(updatePasswordRequest.name());
        passwordRepository.save(existentPassword);

        return PasswordMapper.buildPasswordResponse(existentPassword);
    }

    @Override
    public void deletePassword(UUID passwordId) {
        ifNotExistsPasswordByIdThrowException(passwordId);
        passwordRepository.deleteById(passwordId);
    }

    private String generatePassword() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] secretKeyBytes = new byte[20];
        secureRandom.nextBytes(secretKeyBytes);

        return Base64.getEncoder()
                .encodeToString(secretKeyBytes);
    }

    private Password findPasswordByIdOrThrowException(UUID passwordId) {
        return passwordRepository.findById(passwordId)
                .orElseThrow(() -> new NoSuchElementException("Password with ID " + passwordId + "does not exist"));
    }

    private void ifNotExistsPasswordByIdThrowException(UUID passwordId) {
        if (!passwordRepository.existsById(passwordId))
            throw new NoSuchElementException("Password with ID " + passwordId + "does not exist");
    }

    private void ifExistsPasswordNameForUserThrowException(UUID userId, String name) {
        if (passwordRepository.findByUserId(userId).stream()
                .anyMatch( password -> password.getName().equals(name) ))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password name " + name + " already exists for that user.");
    }

    private void ifUserNotExistsThrowException(UUID userId) {
        if (!userClient.existsUserById(userId))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User with id " + userId + " does not exist.");
    }
}