package com.renault.garage.infrastructure.adapter.in.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@Schema(description = "Accessory response")
public class AccessoryResponse {
    
    @Schema(description = "Accessory unique identifier")
    private UUID id;
    
    @Schema(description = "Accessory name")
    private String name;
    
    @Schema(description = "Accessory description")
    private String description;
    
    @Schema(description = "Accessory price in EUR")
    private BigDecimal price;
    
    @Schema(description = "Accessory type/category")
    private String type;
    
    @Schema(description = "Vehicle ID this accessory belongs to")
    private UUID vehicleId;
}
