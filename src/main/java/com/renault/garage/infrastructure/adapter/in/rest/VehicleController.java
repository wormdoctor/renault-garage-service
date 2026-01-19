package com.renault.garage.infrastructure.adapter.in.rest;

import com.renault.garage.domain.model.Vehicle;
import com.renault.garage.domain.model.valueobjects.GarageId;
import com.renault.garage.domain.model.valueobjects.VehicleId;
import com.renault.garage.domain.port.in.vehicle.*;
import com.renault.garage.infrastructure.adapter.in.rest.dto.request.AddVehicleRequest;
import com.renault.garage.infrastructure.adapter.in.rest.dto.request.UpdateVehicleRequest;
import com.renault.garage.infrastructure.adapter.in.rest.dto.response.VehicleResponse;
import com.renault.garage.infrastructure.adapter.in.rest.mapper.VehicleRestMapper;
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

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
@Tag(name = "Vehicles", description = "Vehicle management APIs")
public class VehicleController {

    private final AddVehicleUseCase addVehicleUseCase;
    private final UpdateVehicleUseCase updateVehicleUseCase;
    private final DeleteVehicleUseCase deleteVehicleUseCase;
    private final ListVehiclesUseCase listVehiclesUseCase;
    private final VehicleRestMapper vehicleRestMapper;

    @Operation(summary = "Add a new vehicle to a garage")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Vehicle added successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input or garage full"),
        @ApiResponse(responseCode = "404", description = "Garage not found")
    })
    @PostMapping
    public ResponseEntity<VehicleResponse> addVehicle(@Valid @RequestBody AddVehicleRequest request) {
        AddVehicleCommand command = AddVehicleCommand.builder()
            .garageId(GarageId.of(request.getGarageId()))
            .brand(request.getBrand())
            .yearOfManufacture(request.getYearOfManufacture())
            .fuelType(request.getFuelType())
            .model(request.getModel())
            .build();

        Vehicle vehicle = addVehicleUseCase.add(command);
        return new ResponseEntity<>(vehicleRestMapper.toResponse(vehicle), HttpStatus.CREATED);
    }

    @Operation(summary = "Update a vehicle")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehicle updated successfully"),
        @ApiResponse(responseCode = "404", description = "Vehicle not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VehicleResponse> updateVehicle(
            @Parameter(description = "Vehicle ID") @PathVariable UUID id,
            @Valid @RequestBody UpdateVehicleRequest request) {

        UpdateVehicleCommand command = UpdateVehicleCommand.builder()
            .vehicleId(VehicleId.of(id))
            .brand(request.getBrand())
            .yearOfManufacture(request.getYearOfManufacture())
            .fuelType(request.getFuelType())
            .model(request.getModel())
            .build();

        Vehicle vehicle = updateVehicleUseCase.update(command);
        return ResponseEntity.ok(vehicleRestMapper.toResponse(vehicle));
    }

    @Operation(summary = "Delete a vehicle")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vehicle deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Vehicle not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVehicle(@Parameter(description = "Vehicle ID") @PathVariable UUID id) {
        deleteVehicleUseCase.delete(VehicleId.of(id));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "List vehicles by garage")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved vehicles"),
        @ApiResponse(responseCode = "404", description = "Garage not found")
    })
    @GetMapping("/garage/{garageId}")
    public ResponseEntity<List<VehicleResponse>> listVehiclesByGarage(
            @Parameter(description = "Garage ID") @PathVariable UUID garageId) {
        
        List<Vehicle> vehicles = listVehiclesUseCase.listByGarage(GarageId.of(garageId));
        List<VehicleResponse> response = vehicles.stream()
            .map(vehicleRestMapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "List vehicles by model across all garages")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved vehicles")
    })
    @GetMapping("/model/{model}")
    public ResponseEntity<List<VehicleResponse>> listVehiclesByModel(
            @Parameter(description = "Vehicle model") @PathVariable String model) {
        
        List<Vehicle> vehicles = listVehiclesUseCase.listByModel(model);
        List<VehicleResponse> response = vehicles.stream()
            .map(vehicleRestMapper::toResponse)
            .collect(Collectors.toList());
        
        return ResponseEntity.ok(response);
    }
}
