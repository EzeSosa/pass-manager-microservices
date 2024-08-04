package com.esosa.auth_service.services.interfaces;

import com.esosa.auth_service.controllers.requests.AuthRequest;
import com.esosa.auth_service.controllers.responses.AuthResponse;

public interface IAuthService {
    void register(AuthRequest authRequest);
    AuthResponse login(AuthRequest authRequest);
    Boolean validateToken(String accessToken);
}