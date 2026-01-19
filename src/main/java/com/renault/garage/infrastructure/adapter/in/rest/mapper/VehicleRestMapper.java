package com.renault.garage.infrastructure.adapter.in.rest.mapper;

import com.renault.garage.domain.model.Vehicle;
import com.renault.garage.infrastructure.adapter.in.rest.dto.response.VehicleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VehicleRestMapper {

    private final AccessoryRestMapper accessoryRestMapper;

    public VehicleResponse toResponse(Vehicle vehicle) {
        VehicleResponse.VehicleResponseBuilder builder = VehicleResponse.builder()
            .id(vehicle.getId().getValue())
            .brand(vehicle.getBrand())
            .yearOfManufacture(vehicle.getYearOfManufacture())
            .fuelType(vehicle.getFuelType())
            .model(vehicle.getModel())
            .garageId(vehicle.getGarageId().getValue());
        
        // Add accessories if they exist
        if (vehicle.getAccessories() != null && !vehicle.getAccessories().isEmpty()) {
            builder.accessories(vehicle.getAccessories().stream()
                .map(accessoryRestMapper::toResponse)
                .collect(Collectors.toList()));
        } else {
            builder.accessories(new ArrayList<>());
        }
        
        return builder.build();
    }
}
