package com.productStore.product_service.service;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.productStore.product_service.dto.ProductDTO;
import com.productStore.product_service.entity.Product;
import com.productStore.product_service.mapper.ProductMapper;
import com.productStore.product_service.repository.ProductRepository;
import com.productStore.product_service.service.impl.ProductServiceImpl;

public class ProductServiceImplTest {

    private final ProductRepository repo = mock(ProductRepository.class);
    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);
    private ProductService service;

    @BeforeEach
    void setUp() {
        service = new ProductServiceImpl(repo, mapper);
    }

    @Test
    void testCreateProduct() {
        Product product = Product.builder().id(1L).name("Phone").description("Smart").price(999.0).stock(10).build();
        when(repo.save(any())).thenReturn(product);
        ProductDTO result = service.createProduct(mapper.toDTO(product));
        assertEquals("Phone", result.getName());
    }
}