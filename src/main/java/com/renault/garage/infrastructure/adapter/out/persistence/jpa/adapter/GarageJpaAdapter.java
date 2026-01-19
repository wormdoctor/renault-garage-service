package com.renault.garage.infrastructure.adapter.out.persistence.jpa.adapter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.renault.garage.domain.model.Accessory;
import com.renault.garage.domain.model.Garage;
import com.renault.garage.domain.model.OpeningTime;
import com.renault.garage.domain.model.Vehicle;
import com.renault.garage.domain.model.valueobjects.AccessoryId;
import com.renault.garage.domain.model.valueobjects.GarageId;
import com.renault.garage.domain.model.valueobjects.VehicleId;
import com.renault.garage.domain.port.out.persistence.GarageRepository;
import com.renault.garage.infrastructure.adapter.out.persistence.jpa.entity.AccessoryEntity;
import com.renault.garage.infrastructure.adapter.out.persistence.jpa.entity.GarageEntity;
import com.renault.garage.infrastructure.adapter.out.persistence.jpa.entity.VehicleEntity;
import com.renault.garage.infrastructure.adapter.out.persistence.jpa.repository.GarageJpaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class GarageJpaAdapter implements GarageRepository {

    private final GarageJpaRepository jpaRepository;
    private final ObjectMapper objectMapper;

    @Override
    public Garage save(Garage garage) {
        log.debug("Saving garage: {}", garage.getName());
        GarageEntity entity = toEntity(garage);
        GarageEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Garage> findById(GarageId id) {
        log.debug("Finding garage by id: {}", id);
        return jpaRepository.findById(id.getValue())
                .map(this::toDomain);
    }

    @Override
    public Page<Garage> findAll(Pageable pageable) {
        log.debug("Finding all garages with pageable: {}", pageable);
        return jpaRepository.findAll(pageable)
                .map(this::toDomain);
    }

    @Override
    public void deleteById(GarageId id) {
        log.debug("Deleting garage by id: {}", id);
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public Page<Garage> findByVehicleType(String vehicleType, Pageable pageable) {
        log.debug("Finding garages by vehicle type: {}", vehicleType);
        return jpaRepository.findByVehiclesFuelType(vehicleType, pageable)
                .map(this::toDomain);
    }

    @Override
    public Page<Garage> findByAccessory(String accessoryName, Pageable pageable) {
        log.debug("Finding garages by accessory: {}", accessoryName);
        return jpaRepository.findByVehiclesAccessoriesName(accessoryName, pageable)
                .map(this::toDomain);
    }

    @Override
    public boolean existsById(GarageId id) {
        return jpaRepository.existsById(id.getValue());
    }

    private GarageEntity toEntity(Garage garage) {
        GarageEntity entity = GarageEntity.builder()
                .id(garage.getId().getValue())
                .name(garage.getName())
                .address(garage.getAddress())
                .telephone(garage.getTelephone())
                .email(garage.getEmail())
                .build();

        // Serialize opening hours
        if (garage.getOpeningHours() != null) {
            Map<DayOfWeek, String> serialized = serializeOpeningHours(garage.getOpeningHours());
            entity.setOpeningHoursJson(serialized);
        }

        return entity;
    }

    private Garage toDomain(GarageEntity entity) {
        Map<DayOfWeek, List<OpeningTime>> openingHours = new HashMap<>();

        // Safely deserialize opening hours
        if (entity.getOpeningHoursJson() != null && !entity.getOpeningHoursJson().isEmpty()) {
            try {
                openingHours = deserializeOpeningHours(entity.getOpeningHoursJson());
            } catch (Exception e) {
                log.error("Failed to deserialize opening hours for garage {}: {}",
                        entity.getId(), e.getMessage());
                // Return empty map instead of failing
                openingHours = new HashMap<>();
            }
        }

        return Garage.builder()
                .id(GarageId.of(entity.getId()))
                .name(entity.getName())
                .address(entity.getAddress())
                .telephone(entity.getTelephone())
                .email(entity.getEmail())
                .openingHours(openingHours)
                .vehicles(mapVehicles(entity.getVehicles()))
                .build();
    }

    private List<Vehicle> mapVehicles(List<VehicleEntity> vehicleEntities) {
        if (vehicleEntities == null) {
            return new ArrayList<>();
        }
        return vehicleEntities.stream()
                .map(this::mapVehicle)
                .collect(Collectors.toList());
    }

    private Vehicle mapVehicle(VehicleEntity entity) {
        return Vehicle.builder()
                .id(VehicleId.of(entity.getId()))
                .brand(entity.getBrand())
                .yearOfManufacture(entity.getYearOfManufacture())
                .fuelType(entity.getFuelType())
                .model(entity.getModel())
                .garageId(GarageId.of(entity.getGarage().getId()))
                .accessories(mapAccessories(entity.getAccessories()))
                .build();
    }

    private List<Accessory> mapAccessories(List<AccessoryEntity> accessoryEntities) {
        if (accessoryEntities == null) {
            return new ArrayList<>();
        }
        return accessoryEntities.stream()
                .map(this::mapAccessory)
                .collect(Collectors.toList());
    }

    private Accessory mapAccessory(AccessoryEntity entity) {
        return Accessory.builder()
                .id(AccessoryId.of(entity.getId()))
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .type(entity.getType())
                .vehicleId(VehicleId.of(entity.getVehicle().getId()))
                .build();
    }

    private Map<DayOfWeek, String> serializeOpeningHours(Map<DayOfWeek, List<OpeningTime>> openingHours) {
        Map<DayOfWeek, String> result = new HashMap<>();

        for (Map.Entry<DayOfWeek, List<OpeningTime>> entry : openingHours.entrySet()) {
            try {
                String json = objectMapper.writeValueAsString(entry.getValue());
                result.put(entry.getKey(), json);
            } catch (Exception e) {
                log.error("Failed to serialize opening hours for day {}: {}",
                        entry.getKey(), e.getMessage());
                throw new RuntimeException("Failed to serialize opening hours", e);
            }
        }

        return result;
    }

    private Map<DayOfWeek, List<OpeningTime>> deserializeOpeningHours(Map<DayOfWeek, String> json) {
        Map<DayOfWeek, List<OpeningTime>> result = new HashMap<>();

        for (Map.Entry<DayOfWeek, String> entry : json.entrySet()) {
            try {
                List<OpeningTime> times = objectMapper.readValue(
                        entry.getValue(),
                        new TypeReference<List<OpeningTime>>() {
                        });
                result.put(entry.getKey(), times);
            } catch (Exception e) {
                log.error("Failed to deserialize opening hours for day {}: {}. JSON: {}",
                        entry.getKey(), e.getMessage(), entry.getValue());
                // Skip this day instead of failing completely
                log.warn("Skipping opening hours for {}", entry.getKey());
            }
        }

        return result;
    }
}
