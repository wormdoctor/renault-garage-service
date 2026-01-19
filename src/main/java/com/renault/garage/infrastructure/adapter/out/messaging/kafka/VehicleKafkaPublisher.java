package com.renault.garage.infrastructure.adapter.out.messaging.kafka;

import com.renault.garage.domain.model.Vehicle;
import com.renault.garage.domain.port.out.messaging.VehicleEventPublisher;
import com.renault.garage.infrastructure.adapter.out.messaging.kafka.event.VehicleEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class VehicleKafkaPublisher implements VehicleEventPublisher {

    private static final String TOPIC = "vehicle-events";

    private final KafkaTemplate<String, VehicleEvent> kafkaTemplate;

    @Override
    public void publishVehicleCreated(Vehicle vehicle) {
        VehicleEvent event = buildEvent(vehicle, "VEHICLE_CREATED");
        sendEvent(event);
    }

    @Override
    public void publishVehicleUpdated(Vehicle vehicle) {
        VehicleEvent event = buildEvent(vehicle, "VEHICLE_UPDATED");
        sendEvent(event);
    }

    @Override
    public void publishVehicleDeleted(Vehicle vehicle) {
        VehicleEvent event = buildEvent(vehicle, "VEHICLE_DELETED");
        sendEvent(event);
    }

    private VehicleEvent buildEvent(Vehicle vehicle, String eventType) {
        return VehicleEvent.builder()
                .eventId(UUID.randomUUID().toString())
                .eventType(eventType)
                .vehicleId(vehicle.getId().getValue().toString())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .yearOfManufacture(vehicle.getYearOfManufacture())
                .fuelType(vehicle.getFuelType())
                .garageId(vehicle.getGarageId().getValue().toString())
                .build();
    }

    private void sendEvent(VehicleEvent event) {
        kafkaTemplate.send(TOPIC, event.getVehicleId(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Published vehicle event: {} for vehicle: {}",
                                event.getEventType(), event.getVehicleId());
                    } else {
                        log.error("Failed to publish vehicle event: {}", event.getEventType(), ex);
                    }
                });
    }
}
