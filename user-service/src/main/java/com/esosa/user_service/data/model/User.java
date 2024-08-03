package com.esosa.user_service.data.model;

import com.esosa.user_service.data.enums.Role;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class User {
    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private final Role role = Role.USER;

    @Id
    private final UUID id = UUID.randomUUID();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User() {}

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public UUID getId() {
        return id;
    }
}