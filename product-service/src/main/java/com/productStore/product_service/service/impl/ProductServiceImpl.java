package com.productStore.product_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.productStore.product_service.dto.ProductDTO;
import com.productStore.product_service.entity.Product;
import com.productStore.product_service.exception.ResourceNotFoundException;
import com.productStore.product_service.mapper.ProductMapper;
import com.productStore.product_service.repository.ProductRepository;
import com.productStore.product_service.service.ProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repo;
    private final ProductMapper mapper;

    @Override
    public ProductDTO createProduct(ProductDTO dto) {
        return mapper.toDTO(repo.save(mapper.toEntity(dto)));
    }

    @Override
    public ProductDTO getProduct(Long id) {
        return mapper.toDTO(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found")));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO dto) {
        Product existing = repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Product not found"));
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setStock(dto.getStock());
        return mapper.toDTO(repo.save(existing));
    }

    @Override
    public void deleteProduct(Long id) {
        repo.deleteById(id);
    }
}