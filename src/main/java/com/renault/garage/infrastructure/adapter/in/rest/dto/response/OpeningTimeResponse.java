package com.renault.garage.infrastructure.adapter.in.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Opening time period")
public class OpeningTimeResponse {
    
    @Schema(description = "Start time", example = "09:00")
    private String startTime;
    
    @Schema(description = "End time", example = "18:00")
    private String endTime;
}
