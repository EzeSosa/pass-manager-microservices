package com.esosa.user_service.controllers.implementations;

import com.esosa.user_service.controllers.interfaces.IUserController;
import com.esosa.user_service.controllers.requests.UserRequest;
import com.esosa.user_service.controllers.responses.PasswordResponse;
import com.esosa.user_service.controllers.responses.UserResponse;
import com.esosa.user_service.services.interfaces.IUserService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class UserController implements IUserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public UserResponse saveUser(UserRequest userRequest) {
        return userService.saveUser(userRequest);
    }

    @Override
    public Page<PasswordResponse> getUserPasswords(UUID userId, int size, int pageNumber) {
        return userService.getUserPasswords(userId, size, pageNumber);
    }

    @Override
    public Boolean existsUserById(UUID userId) {
        return userService.existsUserById(userId);
    }
}