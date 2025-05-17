package com.productStore.sale_service.service;

import java.util.List;

import com.productStore.sale_service.dto.SaleDTO;


public interface SaleService {
    SaleDTO createSale(SaleDTO dto);
    SaleDTO getSale(Long id);
    List<SaleDTO> getAllSales();
    void cancelSale(Long id);
}
