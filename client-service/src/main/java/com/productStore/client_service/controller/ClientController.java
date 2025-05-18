package com.productStore.client_service.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productStore.client_service.dto.ClientDTO;
import com.productStore.client_service.service.ClientService;
import com.productStore.client_service.mapper.ClientMapper;
import com.productStore.client_service.repository.ClientRepository;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/clients")
@RequiredArgsConstructor
public class ClientController {
    private final ClientService service;
    private final ClientMapper mapper;
    private final ClientRepository repo;

    @Operation(summary = "Get client by ID", description = "Retrieve client details by client ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "client found and returned"),
        @ApiResponse(responseCode = "404", description = "client with given ID not found")
    })
    @GetMapping("/{id}")
    public ClientDTO get(
        @Parameter(description = "ID of the client to retrieve", required = true, example = "1")
        @PathVariable Long id) {
        return service.getClient(id);
    }

    @Operation(summary = "Get all clients", description = "Retrieve a list of all clients")
    @ApiResponse(responseCode = "200", description = "List of clients returned")
    @GetMapping
    public List<ClientDTO> getAll() {
        return service.getAllClients();
    }

    @Operation(summary = "Delete a client", description = "Delete the client identified by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "client successfully deleted"),
        @ApiResponse(responseCode = "404", description = "client with given ID not found")
    })
    @DeleteMapping("/{id}")
    public void delete(
        @Parameter(description = "ID of the client to delete", required = true, example = "1")
        @PathVariable Long id) {
        service.deleteClient(id);
    }

    @Operation(summary = "Register a new client", description = "Registers a new client with username and password")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Client registered successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid registration data")
    })
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody ClientDTO dto) {
        try {
            ClientDTO createdClient = service.createClient(dto);
            return new ResponseEntity<>(createdClient, HttpStatus.CREATED);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody ClientDTO clientDTO) {
        try {
            String token = service.authenticate(clientDTO.getUsername(), clientDTO.getPassword());

            ClientDTO client = mapper.toDTO(
                repo.findByUsername(clientDTO.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"))
            );

            return ResponseEntity.ok().body(Map.of(
                "token", token,
                "id", client.getId()
            ));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
