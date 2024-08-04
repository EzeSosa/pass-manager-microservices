package com.esosa.auth_service.services.implementations;

import com.esosa.auth_service.client.UserClient;
import com.esosa.auth_service.controllers.requests.AuthRequest;
import com.esosa.auth_service.controllers.responses.AuthResponse;
import com.esosa.auth_service.controllers.responses.UserResponse;
import com.esosa.auth_service.services.interfaces.IAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService implements IAuthService {
    private final UserClient userClient;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserClient userClient, JWTService jwtService, AuthenticationManager authenticationManager) {
        this.userClient = userClient;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public void register(AuthRequest authRequest) {
        userClient.saveUser(authRequest);
    }

    @Override
    public AuthResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.username(), authRequest.password())
        );
        if (!authentication.isAuthenticated())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid credentials.");

        UserResponse existentUser = userClient.getUserByUsername(authRequest.username());
        String accessToken = generateAccessToken(existentUser.username());

        return new AuthResponse(existentUser, accessToken);
    }

    @Override
    public Boolean validateToken(String accessToken) {
        return jwtService.isTokenValid(accessToken);
    }

    private String generateAccessToken(String username) {
        return jwtService.generateToken(username);
    }
}