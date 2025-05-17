package com.productStore.client_service.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.productStore.client_service.dto.ClientDTO;
import com.productStore.client_service.exception.ResourceNotFoundException;
import com.productStore.client_service.mapper.ClientMapper;
import com.productStore.client_service.repository.ClientRepository;
import com.productStore.client_service.security.JwtUtil;
import com.productStore.client_service.service.ClientService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository repo;
    private final ClientMapper mapper;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ClientDTO createClient(ClientDTO dto) {
        dto.setPassword(passwordEncoder.encode(dto.getPassword()));
        return mapper.toDTO(repo.save(mapper.toEntity(dto)));
    }

    @Override
    public ClientDTO getClient(Long id) {
        return mapper.toDTO(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Client not found")));
    }

    @Override
    public List<ClientDTO> getAllClients() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }


    @Override
    public void deleteClient(Long id) {
        repo.deleteById(id);
    }
    
    @Override
    public String authenticate(String username, String password) {
        ClientDTO client = mapper.toDTO(repo.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found")));

        if (!passwordEncoder.matches(password, client.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(client.getUsername());
    }
    
}
