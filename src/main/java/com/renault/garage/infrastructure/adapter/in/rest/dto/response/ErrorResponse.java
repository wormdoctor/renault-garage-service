package com.renault.garage.infrastructure.adapter.in.rest.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
@Builder
@Schema(description = "Error response")
public class ErrorResponse {
    
    @Schema(description = "Error timestamp")
    @Builder.Default
    private Instant timestamp = Instant.now();
    
    @Schema(description = "HTTP status code")
    private int status;
    
    @Schema(description = "Error type")
    private String error;
    
    @Schema(description = "Error message")
    private String message;
    
    @Schema(description = "Request path")
    private String path;
    
    @Schema(description = "Validation errors (if any)")
    private List<ValidationError> validationErrors;
    
    @Data
    @Builder
    public static class ValidationError {
        @Schema(description = "Field name")
        private String field;
        
        @Schema(description = "Error message")
        private String message;
    }
}
