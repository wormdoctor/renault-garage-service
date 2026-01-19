package com.renault.garage.infrastructure.adapter.in.rest.mapper;

import com.renault.garage.domain.model.Accessory;
import com.renault.garage.infrastructure.adapter.in.rest.dto.response.AccessoryResponse;
import org.springframework.stereotype.Component;

@Component
public class AccessoryRestMapper {

    public AccessoryResponse toResponse(Accessory accessory) {
        return AccessoryResponse.builder()
                .id(accessory.getId().getValue())
                .name(accessory.getName())
                .description(accessory.getDescription())
                .price(accessory.getPrice())
                .type(accessory.getType())
                .vehicleId(accessory.getVehicleId().getValue())
                .build();
    }
}
