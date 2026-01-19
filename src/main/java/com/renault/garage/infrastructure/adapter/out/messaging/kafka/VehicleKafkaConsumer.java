package com.renault.garage.infrastructure.adapter.out.messaging.kafka;

import com.renault.garage.infrastructure.adapter.out.messaging.kafka.event.VehicleEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class VehicleKafkaConsumer {

    @KafkaListener(topics = "vehicle-events", groupId = "${spring.kafka.consumer.group-id}", containerFactory = "kafkaListenerContainerFactory")
    public void consumeVehicleEvent(
            @Payload VehicleEvent event,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
            @Header(KafkaHeaders.OFFSET) long offset) {
        log.info("Received vehicle event: {} from partition: {}, offset: {}",
                event.getEventType(), partition, offset);

        try {
            switch (event.getEventType()) {
                case "VEHICLE_CREATED":
                    handleVehicleCreated(event);
                    break;
                case "VEHICLE_UPDATED":
                    handleVehicleUpdated(event);
                    break;
                case "VEHICLE_DELETED":
                    handleVehicleDeleted(event);
                    break;
                default:
                    log.warn("Unknown event type: {}", event.getEventType());
            }
        } catch (Exception e) {
            log.error("Error processing vehicle event: {}", event.getEventType(), e);
            // Here you could implement retry logic or send to DLQ
        }
    }

    private void handleVehicleCreated(VehicleEvent event) {
        log.info("Processing vehicle created: {} - {} {}",
                event.getVehicleId(), event.getBrand(), event.getModel());
        // Add your business logic here
        // For example: send notification, update analytics, etc.
    }

    private void handleVehicleUpdated(VehicleEvent event) {
        log.info("Processing vehicle updated: {}", event.getVehicleId());
        // Add your business logic here
    }

    private void handleVehicleDeleted(VehicleEvent event) {
        log.info("Processing vehicle deleted: {}", event.getVehicleId());
        // Add your business logic here
    }
}
