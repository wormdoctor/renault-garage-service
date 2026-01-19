package com.renault.garage.infrastructure.adapter.in.rest;

import com.renault.garage.domain.model.Garage;
import com.renault.garage.domain.model.valueobjects.GarageId;
import com.renault.garage.domain.port.in.garage.*;
import com.renault.garage.infrastructure.adapter.in.rest.dto.request.CreateGarageRequest;
import com.renault.garage.infrastructure.adapter.in.rest.dto.request.UpdateGarageRequest;
import com.renault.garage.infrastructure.adapter.in.rest.dto.response.GarageResponse;
import com.renault.garage.infrastructure.adapter.in.rest.mapper.GarageRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/garages")
@RequiredArgsConstructor
@Tag(name = "Garages", description = "Garage management APIs")
public class GarageController {

    private final CreateGarageUseCase createGarageUseCase;
    private final UpdateGarageUseCase updateGarageUseCase;
    private final DeleteGarageUseCase deleteGarageUseCase;
    private final GetGarageUseCase getGarageUseCase;
    private final SearchGarageUseCase searchGarageUseCase;
    private final GarageRestMapper garageRestMapper;

    @Operation(summary = "Create a new garage")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Garage created successfully", 
            content = @Content(schema = @Schema(implementation = GarageResponse.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PostMapping
    public ResponseEntity<GarageResponse> createGarage(@Valid @RequestBody CreateGarageRequest request) {
        CreateGarageCommand command = garageRestMapper.toCommand(request);
        Garage garage = createGarageUseCase.create(command);
        return new ResponseEntity<>(garageRestMapper.toResponse(garage), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an existing garage")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Garage updated successfully", 
            content = @Content(schema = @Schema(implementation = GarageResponse.class))),
        @ApiResponse(responseCode = "404", description = "Garage not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<GarageResponse> updateGarage(
            @Parameter(description = "Garage ID") @PathVariable UUID id,
            @Valid @RequestBody UpdateGarageRequest request) {
        
        UpdateGarageCommand command = garageRestMapper.toUpdateCommand(GarageId.of(id), request);
        Garage garage = updateGarageUseCase.update(command);
        return ResponseEntity.ok(garageRestMapper.toResponse(garage));
    }

    @Operation(summary = "Delete a garage")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Garage deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Garage not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGarage(@Parameter(description = "Garage ID") @PathVariable UUID id) {
        deleteGarageUseCase.delete(GarageId.of(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get a garage by ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the garage", 
            content = @Content(schema = @Schema(implementation = GarageResponse.class))),
        @ApiResponse(responseCode = "404", description = "Garage not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<GarageResponse> getGarage(@Parameter(description = "Garage ID") @PathVariable UUID id) {
        Garage garage = getGarageUseCase.findById(GarageId.of(id));
        return ResponseEntity.ok(garageRestMapper.toResponse(garage));
    }

    @Operation(summary = "Get all garages with pagination and sorting")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved garages")
    })
    @GetMapping
    public ResponseEntity<Page<GarageResponse>> getAllGarages(
            @PageableDefault(size = 20, sort = "name") Pageable pageable) {
        
        Page<Garage> garages = searchGarageUseCase.findAll(pageable);
        return ResponseEntity.ok(garages.map(garageRestMapper::toResponse));
    }

    @Operation(summary = "Search garages by vehicle type")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved garages")
    })
    @GetMapping("/search/by-vehicle-type")
    public ResponseEntity<Page<GarageResponse>> searchByVehicleType(
            @Parameter(description = "Vehicle fuel type (Electric, Diesel, Hybrid, Petrol)") 
            @RequestParam String vehicleType,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<Garage> garages = searchGarageUseCase.findByVehicleType(vehicleType, pageable);
        return ResponseEntity.ok(garages.map(garageRestMapper::toResponse));
    }

    @Operation(summary = "Search garages by accessory")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved garages")
    })
    @GetMapping("/search/by-accessory")
    public ResponseEntity<Page<GarageResponse>> searchByAccessory(
            @Parameter(description = "Accessory name (partial match supported)") 
            @RequestParam String accessoryName,
            @PageableDefault(size = 20) Pageable pageable) {
        
        Page<Garage> garages = searchGarageUseCase.findByAccessory(accessoryName, pageable);
        return ResponseEntity.ok(garages.map(garageRestMapper::toResponse));
    }
}
