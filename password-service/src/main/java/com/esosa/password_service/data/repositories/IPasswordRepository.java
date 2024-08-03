package com.esosa.password_service.data.repositories;

import com.esosa.password_service.data.model.Password;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface IPasswordRepository extends JpaRepository<Password, UUID> {
    Page<Password> findByUserId(PageRequest pageRequest, UUID userId);
    List<Password> findByUserId(UUID userId);
}