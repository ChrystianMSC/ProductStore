package com.productStore.product_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productStore.product_service.dto.ProductDTO;
import com.productStore.product_service.service.ProductService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService service;

    @Operation(summary = "Create a new product", description = "Creates a new product with the provided details")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid product data provided")
    })
    @PostMapping
    public ProductDTO create(
        @Parameter(description = "Product data to create", required = true)
        @Valid @RequestBody ProductDTO dto) {
        return service.createProduct(dto);
    }

    @Operation(summary = "Get product by ID", description = "Retrieve product details by product ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product found and returned"),
        @ApiResponse(responseCode = "404", description = "Product with given ID not found")
    })
    @GetMapping("/{id}")
    public ProductDTO get(
        @Parameter(description = "ID of the product to retrieve", required = true, example = "1")
        @PathVariable Long id) {
        return service.getProduct(id);
    }

    @Operation(summary = "Get all products", description = "Retrieve a list of all products")
    @ApiResponse(responseCode = "200", description = "List of products returned")
    @GetMapping
    public List<ProductDTO> getAll() {
        return service.getAllProducts();
    }

    @Operation(summary = "Update an existing product", description = "Update the product identified by ID with new data")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Product successfully updated"),
        @ApiResponse(responseCode = "400", description = "Invalid product data provided"),
        @ApiResponse(responseCode = "404", description = "Product with given ID not found")
    })
    @PutMapping("/{id}")
    public ProductDTO update(
        @Parameter(description = "ID of the product to update", required = true, example = "1")
        @PathVariable Long id,
        @Parameter(description = "Updated product data", required = true)
        @Valid @RequestBody ProductDTO dto) {
        return service.updateProduct(id, dto);
    }

    @Operation(summary = "Delete a product", description = "Delete the product identified by ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Product successfully deleted"),
        @ApiResponse(responseCode = "404", description = "Product with given ID not found")
    })
    @DeleteMapping("/{id}")
    public void delete(
        @Parameter(description = "ID of the product to delete", required = true, example = "1")
        @PathVariable Long id) {
        service.deleteProduct(id);
    }
}
