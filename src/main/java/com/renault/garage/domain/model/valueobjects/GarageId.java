package com.renault.garage.domain.model.valueobjects;

import lombok.Value;

import java.util.UUID;

@Value(staticConstructor = "of")
public class GarageId {
    UUID value;
    
    public static GarageId generate() {
        return of(UUID.randomUUID());
    }
    
    public static GarageId fromString(String id) {
        return of(UUID.fromString(id));
    }
}
