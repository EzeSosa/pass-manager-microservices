package com.esosa.password_service.interceptor;

import com.esosa.password_service.client.AuthClient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    private final AuthClient authClient;

    public AuthInterceptor(AuthClient authClient) {
        this.authClient = authClient;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("GET"))
            return true;

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer "))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Authentication failed.");

        String accessToken = authHeader.substring(7);

        try { return authClient.validateToken(accessToken); }
        catch (Exception ex) { throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bad token."); }
    }
}