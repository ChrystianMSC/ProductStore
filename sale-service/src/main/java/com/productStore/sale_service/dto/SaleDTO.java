package com.productStore.sale_service.dto;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Sale Data Transfer Object representing sale details")
public class SaleDTO {
    @Schema(description = "Unique identifier of the sale", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Unique identifier of the client", example = "1", required = true)
    private Long clientId;

    @Schema(description = "Unique identifier of the product", example = "1", required = true)
    private Long productId;

    @Schema(description = "Name of the product", example = "Wireles Mouse", required = true)
    private String productName;

    @Schema(description = "Quantity of the product that was bought", example = "100", required = true)
    private int quantity;

    @Schema(
        description = "Timestamp when the sale was created. Automatically set by the system.",
        example = "2025-05-15T14:30:00Z",
        accessMode = Schema.AccessMode.READ_ONLY,
        type = "string",
        format = "date-time"
    )
    private Date saleDate;
}
