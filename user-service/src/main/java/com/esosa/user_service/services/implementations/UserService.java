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
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(IUserRepository userRepository, PasswordClient passwordClient, UserMapper userMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordClient = passwordClient;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        ifExistsByUsernameThrowException(userRequest.username());
        User newUser = userMapper.buildUser(userRequest, passwordEncoder);
        userRepository.save(newUser);
        return userMapper.buildUserResponse(newUser);
    }

    @Override
    public Page<PasswordResponse> getUserPasswords(UUID userId, int size, int pageNumber) {
        PasswordPageResponse passwordPageResponse = passwordClient.getPasswordsByUserId(userId, size, pageNumber);
        return buildPasswordResponsePage(passwordPageResponse);
    }

    @Override
    public Boolean existsUserById(UUID userId) {
        return userRepository.existsById(userId);
    }

    @Override
    public User findUserByIdOrThrowException(UUID userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("User with id " + userId + " does not exist."));
    }

    @Override
    public User findUserByUsernameOrThrowException(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User with username " + username + " does not exist."));
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
}