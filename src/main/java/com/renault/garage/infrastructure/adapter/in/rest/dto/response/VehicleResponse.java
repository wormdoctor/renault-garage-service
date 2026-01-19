package com.renault.garage.infrastructure.adapter.in.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Schema(description = "Vehicle response")
public class VehicleResponse {
    
    @Schema(description = "Vehicle unique identifier")
    private UUID id;
    
    @Schema(description = "Vehicle brand")
    private String brand;
    
    @Schema(description = "Year of manufacture")
    private Integer yearOfManufacture;
    
    @Schema(description = "Fuel type")
    private String fuelType;
    
    @Schema(description = "Vehicle model")
    private String model;
    
    @Schema(description = "Garage ID where vehicle is stored")
    private UUID garageId;
    
    @Schema(description = "List of accessories")
    private List<AccessoryResponse> accessories;
}
