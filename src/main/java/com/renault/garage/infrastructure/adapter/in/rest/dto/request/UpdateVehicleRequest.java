package com.renault.garage.infrastructure.adapter.in.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
@Schema(description = "Request to update a vehicle")
public class UpdateVehicleRequest {
    
    @NotBlank(message = "Brand is required")
    @Schema(description = "Vehicle brand", example = "Renault")
    private String brand;
    
    @NotNull(message = "Year of manufacture is required")
    @Min(value = 1900, message = "Year must be after 1900")
    @Max(value = 2100, message = "Year must be before 2100")
    @Schema(description = "Year of manufacture", example = "2024")
    private Integer yearOfManufacture;
    
    @NotBlank(message = "Fuel type is required")
    @Schema(description = "Fuel type", example = "Electric")
    private String fuelType;
    
    @Schema(description = "Vehicle model", example = "Zoe E-Tech")
    private String model;
}
