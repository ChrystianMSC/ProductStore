package com.productStore.client_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.productStore.client_service.entity.Client;

public interface ClientRepository  extends JpaRepository<Client, Long> {
    Optional<Client> findByUsername(String username);
}
