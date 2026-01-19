package com.renault.garage.domain.port.in.vehicle;

import com.renault.garage.domain.model.valueobjects.VehicleId;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UpdateVehicleCommand {
    VehicleId vehicleId;
    String brand;
    Integer yearOfManufacture;
    String fuelType;
    String model;
}
