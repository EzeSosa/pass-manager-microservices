package com.esosa.password_service.data.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import java.util.UUID;

@Entity
public class Password {
    private String name;
    private String password;
    private UUID userId;

    @Id
    private final UUID id = UUID.randomUUID();

    public Password(String name, String password, UUID userId) {
        this.name = name;
        this.password = password;
        this.userId = userId;
    }

    public Password() {}

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public UUID getUserId() {
        return this.userId;
    }

    public UUID getId() {
        return this.id;
    }

    public void setName(String name) {
        this.name = name;
    }
}