package com.renault.garage.domain.port.in.accessory;

import com.renault.garage.domain.model.valueobjects.AccessoryId;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class UpdateAccessoryCommand {
    AccessoryId accessoryId;
    String name;
    String description;
    BigDecimal price;
    String type;
}
