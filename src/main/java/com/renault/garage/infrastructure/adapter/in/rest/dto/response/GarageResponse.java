package com.renault.garage.infrastructure.adapter.in.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Data
@Builder
@Schema(description = "Garage response")
public class GarageResponse {
    
    @Schema(description = "Garage unique identifier")
    private UUID id;
    
    @Schema(description = "Garage name")
    private String name;
    
    @Schema(description = "Garage address")
    private String address;
    
    @Schema(description = "Garage telephone")
    private String telephone;
    
    @Schema(description = "Garage email")
    private String email;
    
    @Schema(description = "Opening hours")
    private Map<DayOfWeek, List<OpeningTimeResponse>> openingHours;
    
    @Schema(description = "Available vehicle capacity (max 50)")
    private Integer availableCapacity;
}
