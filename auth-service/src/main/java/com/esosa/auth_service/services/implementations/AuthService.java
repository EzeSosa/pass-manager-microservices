package com.esosa.auth_service.services.implementations;

import com.esosa.auth_service.client.UserClient;
import com.esosa.auth_service.controllers.requests.AuthRequest;
import com.esosa.auth_service.controllers.requests.RefreshTokenRequest;
import com.esosa.auth_service.controllers.responses.AuthResponse;
import com.esosa.auth_service.controllers.responses.RefreshTokenResponse;
import com.esosa.auth_service.controllers.responses.UserResponse;
import com.esosa.auth_service.services.interfaces.IAuthService;
import com.esosa.auth_service.utils.TokenType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

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

    @Value("${jwt.access-token-expiration}") private Long accessTokenExpiration;
    @Value("${jwt.refresh-token-expiration}") private Long refreshTokenExpiration;

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
        String refreshToken = generateRefreshToken(existentUser.username());

        return new AuthResponse(existentUser, accessToken, refreshToken);
    }

    @Override
    public RefreshTokenResponse refresh(RefreshTokenRequest refreshTokenRequest) {
        ifNotRefreshTokenThrowException(refreshTokenRequest.refreshToken());
        ifRefreshTokenNotValidThrowException(refreshTokenRequest.refreshToken());

        String username = jwtService.extractUsernameFromToken(refreshTokenRequest.refreshToken());
        String newAccessToken = generateAccessToken(username);
        return new RefreshTokenResponse(newAccessToken);
    }

    @Override
    public Boolean validateToken(String token) {
        ifNotAccessTokenThrowException(token);
        return jwtService.isTokenValid(token);
    }

    private String generateAccessToken(String username) {
        Date expirationDate = new Date(System.currentTimeMillis() + accessTokenExpiration);
        return jwtService.generateToken(username, expirationDate, TokenType.ACCESS);
    }

    private String generateRefreshToken(String username) {
        Date expirationDate = new Date(System.currentTimeMillis() + refreshTokenExpiration);
        return jwtService.generateToken(username, expirationDate, TokenType.REFRESH);
    }

    private void ifNotAccessTokenThrowException(String token) {
        if (jwtService.extractTokenTypeFromToken(token) != "ACCESS")
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token is not an access token");
    }

    private void ifNotRefreshTokenThrowException(String token) {
        if (jwtService.extractTokenTypeFromToken(token) != "REFRESH")
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Token is not an refresh token");
    }

    private void ifRefreshTokenNotValidThrowException(String refreshToken) {
        if (!validateToken(refreshToken))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Access token is not valid");
    }
}