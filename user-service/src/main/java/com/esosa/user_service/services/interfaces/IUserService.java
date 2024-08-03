package com.esosa.user_service.services.interfaces;

import com.esosa.user_service.controllers.requests.UserRequest;
import com.esosa.user_service.controllers.responses.PasswordResponse;
import com.esosa.user_service.controllers.responses.UserResponse;
import com.esosa.user_service.data.model.User;
import org.springframework.data.domain.Page;

import java.util.UUID;

public interface IUserService {
    UserResponse saveUser(UserRequest userRequest);
    Page<PasswordResponse> getUserPasswords(UUID userId, int size, int pageNumber);
    Boolean existsUserById(UUID userId);
    User findUserByIdOrThrowException(UUID userId);
    User findUserByUsernameOrThrowException(String username);
}