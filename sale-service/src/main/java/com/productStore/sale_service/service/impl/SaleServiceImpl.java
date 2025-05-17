package com.productStore.sale_service.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.productStore.sale_service.dto.SaleDTO;
import com.productStore.sale_service.exception.ResourceNotFoundException;
import com.productStore.sale_service.mapper.SaleMapper;
import com.productStore.sale_service.repository.SaleRepository;
import com.productStore.sale_service.service.SaleService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
     private final SaleRepository repo;
    private final SaleMapper mapper;

    @Override
    public SaleDTO createSale(SaleDTO dto) {
        return mapper.toDTO(repo.save(mapper.toEntity(dto)));
    }

    @Override
    public SaleDTO getSale(Long id) {
        return mapper.toDTO(repo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Sale not found")));
    }

    @Override
    public List<SaleDTO> getAllSales() {
        return repo.findAll().stream().map(mapper::toDTO).toList();
    }


    @Override
    public void cancelSale(Long id) {
        repo.deleteById(id);
    }
}
