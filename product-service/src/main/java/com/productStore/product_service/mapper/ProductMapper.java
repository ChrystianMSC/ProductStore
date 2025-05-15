package com.productStore.product_service.mapper;

import org.mapstruct.Mapper;

import com.productStore.product_service.dto.ProductDTO;
import com.productStore.product_service.entity.Product;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDTO toDTO(Product product);
    Product toEntity(ProductDTO dto);
}
