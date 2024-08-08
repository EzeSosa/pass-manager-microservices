package com.esosa.auth_service.controllers.implementations;

import com.esosa.auth_service.controllers.interfaces.IAuthController;
import com.esosa.auth_service.controllers.requests.AuthRequest;
import com.esosa.auth_service.controllers.requests.RefreshTokenRequest;
import com.esosa.auth_service.controllers.responses.AuthResponse;
import com.esosa.auth_service.controllers.responses.RefreshTokenResponse;
import com.esosa.auth_service.services.interfaces.IAuthService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController implements IAuthController {
    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @Override
    public void register(AuthRequest authRequest) {
        authService.register(authRequest);
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        return authService.login(authRequest);
    }

    @Override
    public RefreshTokenResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        return authService.refresh(refreshTokenRequest);
    }

    @Override
    public Boolean validateToken(String accessToken) {
        return authService.validateToken(accessToken);
    }
}