package com.renault.garage.infrastructure.adapter.in.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Schema(description = "Request to add an accessory to a vehicle")
public class AddAccessoryRequest {
    
    @NotNull(message = "Vehicle ID is required")
    @Schema(description = "Vehicle ID where the accessory will be added")
    private UUID vehicleId;
    
    @NotBlank(message = "Name is required")
    @Schema(description = "Accessory name", example = "GPS Navigation System")
    private String name;
    
    @NotBlank(message = "Description is required")
    @Schema(description = "Accessory description", example = "Advanced GPS navigation with real-time traffic")
    private String description;
    
    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = true, message = "Price must be positive or zero")
    @Schema(description = "Accessory price in EUR", example = "299.99")
    private BigDecimal price;
    
    @NotBlank(message = "Type is required")
    @Schema(description = "Accessory type/category", example = "Electronics")
    private String type;
}
