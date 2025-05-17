package com.productStore.client_service.service;

import java.util.List;

import com.productStore.client_service.dto.ClientDTO;


public interface ClientService {
    ClientDTO createClient(ClientDTO dto);
    ClientDTO getClient(Long id);
    List<ClientDTO> getAllClients();
    void deleteClient(Long id);
    String authenticate(String username, String password);
}
