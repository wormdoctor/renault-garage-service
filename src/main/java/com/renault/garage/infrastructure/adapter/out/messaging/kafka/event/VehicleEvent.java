package com.renault.garage.infrastructure.adapter.out.messaging.kafka.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleEvent {
    private String eventId;
    private String eventType;
    private String vehicleId;
    private String brand;
    private String model;
    private Integer yearOfManufacture;
    private String fuelType;
    private String garageId;

    @Builder.Default
    private Instant timestamp = Instant.now();
}
