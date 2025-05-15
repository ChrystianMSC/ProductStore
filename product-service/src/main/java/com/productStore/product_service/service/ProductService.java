package com.productStore.product_service.service;

import java.util.List;

import com.productStore.product_service.dto.ProductDTO;

public interface ProductService {
    ProductDTO createProduct(ProductDTO dto);
    ProductDTO getProduct(Long id);
    List<ProductDTO> getAllProducts();
    ProductDTO updateProduct(Long id, ProductDTO dto);
    void deleteProduct(Long id);
}