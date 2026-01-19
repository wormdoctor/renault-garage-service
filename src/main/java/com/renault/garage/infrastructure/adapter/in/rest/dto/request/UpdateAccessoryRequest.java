package com.renault.garage.infrastructure.adapter.in.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Schema(description = "Request to update an accessory")
public class UpdateAccessoryRequest {
    
    @NotBlank(message = "Name is required")
    @Schema(description = "Accessory name", example = "GPS Navigation System Pro")
    private String name;
    
    @NotBlank(message = "Description is required")
    @Schema(description = "Accessory description", example = "Premium GPS navigation with European maps")
    private String description;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be positive or zero")
    @Schema(description = "Accessory price in EUR", example = "349.99")
    private BigDecimal price;
    
    @NotBlank(message = "Type is required")
    @Schema(description = "Accessory type/category", example = "Electronics")
    private String type;
}
