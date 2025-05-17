package com.productStore.client_service.mapper;

import org.mapstruct.Mapper;

import com.productStore.client_service.dto.ClientDTO;
import com.productStore.client_service.entity.Client;

@Mapper(componentModel = "spring")
public interface ClientMapper {
    ClientDTO toDTO(Client product);
    Client toEntity(ClientDTO dto);
}
