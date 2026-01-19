package com.renault.garage.domain.port.in.vehicle;

import com.renault.garage.domain.model.valueobjects.GarageId;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AddVehicleCommand {
    GarageId garageId;
    String brand;
    Integer yearOfManufacture;
    String fuelType;
    String model;
}
