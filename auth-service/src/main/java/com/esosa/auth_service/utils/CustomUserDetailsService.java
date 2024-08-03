package com.esosa.auth_service.utils;

import com.esosa.auth_service.client.UserClient;
import com.esosa.auth_service.controllers.responses.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserClient userClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserResponse existentUser = userClient.getUserByUsername(username);
        return buildUserDetails(existentUser);
    }

    private UserDetails buildUserDetails(UserResponse userResponse) {
        return User.builder()
                .username(userResponse.username())
                .password(userResponse.password())
                .build();
    }
}