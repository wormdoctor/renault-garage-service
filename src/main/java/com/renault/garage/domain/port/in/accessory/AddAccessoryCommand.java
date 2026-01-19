package com.renault.garage.domain.port.in.accessory;

import com.renault.garage.domain.model.valueobjects.VehicleId;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class AddAccessoryCommand {
    VehicleId vehicleId;
    String name;
    String description;
    BigDecimal price;
    String type;
}
