package com.productStore.product_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Product Data Transfer Object representing product details")
public class ProductDTO {

    @Schema(description = "Unique identifier of the product", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Name of the product", example = "Wireless Mouse", required = true)
    private String name;

    @Schema(description = "Detailed description of the product", example = "Ergonomic wireless mouse with USB receiver")
    private String description;

    @Schema(description = "Price of the product in USD", example = "29.99", required = true)
    private double price;

    @Schema(description = "Quantity of the product available in stock", example = "100", required = true)
    private int stock;
}
