package com.renault.garage.domain.model.valueobjects;

import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
public class VehicleId {
    UUID value;

    public static VehicleId generate() {
        return of(UUID.randomUUID());
    }

    public static VehicleId fromString(String id) {
        return of(UUID.fromString(id));
    }
}
