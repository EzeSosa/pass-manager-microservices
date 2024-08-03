package com.esosa.password_service.services.mapper;

import com.esosa.password_service.controllers.requests.CreatePasswordRequest;
import com.esosa.password_service.controllers.responses.PasswordResponse;
import com.esosa.password_service.data.model.Password;
import org.springframework.stereotype.Component;

@Component
public class PasswordMapper {
    public static Password buildPassword(CreatePasswordRequest createPasswordRequest, String password) {
        return new Password(createPasswordRequest.name(), password, createPasswordRequest.userId());
    }

    public static PasswordResponse buildPasswordResponse(Password password) {
        return new PasswordResponse(password.getId(), password.getName(), password.getPassword());
    }
}