package com.esosa.auth_service.services.interfaces;

import com.esosa.auth_service.controllers.requests.AuthRequest;
import com.esosa.auth_service.controllers.requests.RefreshTokenRequest;
import com.esosa.auth_service.controllers.responses.AuthResponse;
import com.esosa.auth_service.controllers.responses.RefreshTokenResponse;

public interface IAuthService {
    void register(AuthRequest authRequest);
    AuthResponse login(AuthRequest authRequest);
    RefreshTokenResponse refresh(RefreshTokenRequest refreshTokenRequest);
    Boolean validateToken(String accessToken);
}