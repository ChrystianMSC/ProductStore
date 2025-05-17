package com.productStore.client_service.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Product Data Transfer Object representing client details")
public class ClientDTO {

    @Schema(description = "Unique identifier of the product", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "UserName of the client", example = "WirelessMouse", required = true)
    private String username;

    @Schema(description = "Password of the client", example = "121231234", required = true)
    private String password;
}
