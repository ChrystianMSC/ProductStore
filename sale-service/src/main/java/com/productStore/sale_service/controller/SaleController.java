package com.productStore.sale_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productStore.sale_service.dto.SaleDTO;
import com.productStore.sale_service.service.SaleService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sales")
@RequiredArgsConstructor
public class SaleController {
    private final SaleService service;

    @Operation(summary = "Create a new sale", description = "Creates a new sale with the provided details")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "sale successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid sale data provided")
    })
    @PostMapping
    public SaleDTO create(
        @Parameter(description = "Sale data to create", required = true)
        @Valid @RequestBody SaleDTO dto) {
        return service.createSale(dto);
    }

    @Operation(summary = "Get sale by ID", description = "Retrieve sale details by sale ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "sale found and returned"),
        @ApiResponse(responseCode = "404", description = "sale with given ID not found")
    })
    @GetMapping("/{id}")
    public SaleDTO get(
        @Parameter(description = "ID of the sale to retrieve", required = true, example = "1")
        @PathVariable Long id) {
        return service.getSale(id);
    }

    @Operation(summary = "Get all sales", description = "Retrieve a list of all sales")
    @ApiResponse(responseCode = "200", description = "List of sales returned")
    @GetMapping
    public List<SaleDTO> getAll() {
        return service.getAllSales();
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
        service.cancelSale(id);
    }
}
