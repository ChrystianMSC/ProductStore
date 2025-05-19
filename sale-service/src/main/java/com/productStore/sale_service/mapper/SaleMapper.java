package com.productStore.sale_service.mapper;

import org.mapstruct.Mapper;

import com.productStore.sale_service.dto.SaleDTO;
import com.productStore.sale_service.entity.Sale;

@Mapper(componentModel = "spring")
public interface SaleMapper {
    SaleDTO toDTO(Sale sale);
    Sale toEntity(SaleDTO dto);
}
