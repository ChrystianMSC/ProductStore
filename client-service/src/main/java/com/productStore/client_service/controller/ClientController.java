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

    @Operation(summary = "Get sale by ID", description = "Retrieve sale details by sale ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "sale found and returned"),
        @ApiResponse(responseCode = "404", description = "sale with given ID not found")
    })
    @GetMapping("/{id}")
    public ClientDTO get(
        @Parameter(description = "ID of the sale to retrieve", required = true, example = "1")
        @PathVariable Long id) {
        return service.getClient(id);
    }

    @Operation(summary = "Get all sales", description = "Retrieve a list of all sales")
    @ApiResponse(responseCode = "200", description = "List of sales returned")
    @GetMapping
    public List<ClientDTO> getAll() {
        return service.getAllClients();
    }

    @Operation(summary = "Delete a sale", description = "Delete the sale identified by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "sale successfully deleted"),
        @ApiResponse(responseCode = "404", description = "sale with given ID not found")
    })
    @DeleteMapping("/{id}")
    public void delete(
        @Parameter(description = "ID of the sale to delete", required = true, example = "1")
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
    public ResponseEntity<?> login(@Valid @RequestBody ClientDTO ClientDTO) {
        try {
            String token = service.authenticate(ClientDTO.getUsername(), ClientDTO.getPassword());
            return ResponseEntity.ok().body(Map.of("token", token));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }
}
