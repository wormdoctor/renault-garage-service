package com.renault.garage.infrastructure.adapter.in.rest;

import com.renault.garage.domain.model.Accessory;
import com.renault.garage.domain.model.valueobjects.AccessoryId;
import com.renault.garage.domain.model.valueobjects.VehicleId;
import com.renault.garage.domain.port.in.accessory.*;
import com.renault.garage.infrastructure.adapter.in.rest.dto.request.AddAccessoryRequest;
import com.renault.garage.infrastructure.adapter.in.rest.dto.request.UpdateAccessoryRequest;
import com.renault.garage.infrastructure.adapter.in.rest.dto.response.AccessoryResponse;
import com.renault.garage.infrastructure.adapter.in.rest.mapper.AccessoryRestMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/accessories")
@RequiredArgsConstructor
@Tag(name = "Accessories", description = "Accessory management APIs")
public class AccessoryController {

    private final AddAccessoryUseCase addAccessoryUseCase;
    private final UpdateAccessoryUseCase updateAccessoryUseCase;
    private final DeleteAccessoryUseCase deleteAccessoryUseCase;
    private final AccessoryRestMapper accessoryRestMapper;

    @Operation(summary = "Add a new accessory to a vehicle")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Accessory added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @PostMapping
    public ResponseEntity<AccessoryResponse> addAccessory(@Valid @RequestBody AddAccessoryRequest request) {
        AddAccessoryCommand command = AddAccessoryCommand.builder()
            .vehicleId(VehicleId.of(request.getVehicleId()))
            .name(request.getName())
            .description(request.getDescription())
            .price(request.getPrice())
            .type(request.getType())
            .build();

        Accessory accessory = addAccessoryUseCase.add(command);
        return new ResponseEntity<>(accessoryRestMapper.toResponse(accessory), HttpStatus.CREATED);
    }

    @Operation(summary = "Update an accessory")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Accessory updated successfully"),
        @ApiResponse(responseCode = "404", description = "Accessory not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AccessoryResponse> updateAccessory(
            @Parameter(description = "Accessory ID") @PathVariable UUID id,
            @Valid @RequestBody UpdateAccessoryRequest request) {

        UpdateAccessoryCommand command = UpdateAccessoryCommand.builder()
            .accessoryId(AccessoryId.of(id))
            .name(request.getName())
            .description(request.getDescription())
            .price(request.getPrice())
            .type(request.getType())
            .build();

        Accessory accessory = updateAccessoryUseCase.update(command);
        return ResponseEntity.ok(accessoryRestMapper.toResponse(accessory));
    }

    @Operation(summary = "Delete an accessory")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Accessory deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Accessory not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccessory(@Parameter(description = "Accessory ID") @PathVariable UUID id) {
        deleteAccessoryUseCase.delete(AccessoryId.of(id));
        return ResponseEntity.noContent().build();
    }
}
