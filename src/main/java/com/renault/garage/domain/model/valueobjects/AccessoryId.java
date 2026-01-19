package com.renault.garage.domain.model.valueobjects;

import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
public class AccessoryId {
    UUID value;

    public static AccessoryId generate() {
        return of(UUID.randomUUID());
    }

    public static AccessoryId fromString(String id) {
        return of(UUID.fromString(id));
    }
}
