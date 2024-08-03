package com.esosa.user_service.services.mapper;

import com.esosa.user_service.controllers.requests.UserRequest;
import com.esosa.user_service.controllers.responses.UserResponse;
import com.esosa.user_service.data.model.User;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public User buildUser(UserRequest userRequest, PasswordEncoder passwordEncoder) {
        String encodedPassword = passwordEncoder.encode(userRequest.password());
        return new User(userRequest.username(), encodedPassword);
    }

    public UserResponse buildUserResponse(User user) {
        return new UserResponse(user.getId(), user.getUsername(), user.getPassword());
    }
}