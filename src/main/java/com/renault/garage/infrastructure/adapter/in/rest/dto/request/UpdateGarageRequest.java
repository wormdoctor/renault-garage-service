package com.renault.garage.infrastructure.adapter.in.rest.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Data
@Schema(description = "Request to update a garage")
public class UpdateGarageRequest {
    
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    @Schema(description = "Garage name", example = "Renault Paris Centre - Updated")
    private String name;
    
    @NotBlank(message = "Address is required")
    @Size(max = 500, message = "Address must not exceed 500 characters")
    @Schema(description = "Garage address", example = "123 Avenue des Champs-Élysées, 75008 Paris")
    private String address;
    
    @NotBlank(message = "Telephone is required")
    @Pattern(regexp = "^\\+?[0-9]{10,15}$", message = "Invalid telephone format")
    @Schema(description = "Garage telephone number", example = "+33123456789")
    private String telephone;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    @Schema(description = "Garage email", example = "paris.updated@renault.fr")
    private String email;
    
    @NotNull(message = "Opening hours are required")
    @Schema(description = "Opening hours per day of week")
    private Map<DayOfWeek, List<OpeningTimeDto>> openingHours;
}
