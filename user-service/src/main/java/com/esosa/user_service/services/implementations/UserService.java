package com.esosa.user_service.services.implementations;

import com.esosa.user_service.client.PasswordClient;
import com.esosa.user_service.controllers.requests.UserRequest;
import com.esosa.user_service.controllers.responses.PasswordPageResponse;
import com.esosa.user_service.controllers.responses.PasswordResponse;
import com.esosa.user_service.controllers.responses.UserResponse;
import com.esosa.user_service.data.model.User;
import com.esosa.user_service.data.repositories.IUserRepository;
import com.esosa.user_service.services.interfaces.IUserService;
import com.esosa.user_service.services.mapper.UserMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
public class UserService implements IUserService {
    private final IUserRepository userRepository;
    private final PasswordClient passwordClient;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, PasswordClient passwordClient, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordClient = passwordClient;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        ifExistsByUsernameThrowException(userRequest.username());
        User newUser = UserMapper.buildUser(userRequest, passwordEncoder);
        userRepository.save(newUser);
        return UserMapper.buildUserResponse(newUser);
    }

    @Override
    public Page<PasswordResponse> getUserPasswords(UUID userId, int size, int pageNumber) {
        ifUserNotExistsByIdThrowException(userId);
        PasswordPageResponse passwordPageResponse = passwordClient.getPasswordsByUserId(userId, size, pageNumber);
        return buildPasswordResponsePage(passwordPageResponse);
    }

    @Override
    public Boolean existsUserById(UUID userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public UserResponse findUserByUsernameOrThrowException(String username) {
        User existentUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User with username " + username + " does not exist."));
        return UserMapper.buildUserResponse(existentUser);
    }

    private Page<PasswordResponse> buildPasswordResponsePage(PasswordPageResponse passwordPageResponse) {
        List<PasswordResponse> passwords = passwordPageResponse.content();
        Pageable pageable = PageRequest.of(passwordPageResponse.number(), passwordPageResponse.size());
        return new PageImpl<>(passwords, pageable, passwordPageResponse.totalElements());
    }

    private void ifExistsByUsernameThrowException(String username) {
        if (userRepository.existsByUsername(username))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists.");
    }

    private void ifUserNotExistsByIdThrowException(UUID userId) {
        if (!existsUserById(userId))
            throw new NoSuchElementException("User with id " + userId + " does not exist.");
    }
}