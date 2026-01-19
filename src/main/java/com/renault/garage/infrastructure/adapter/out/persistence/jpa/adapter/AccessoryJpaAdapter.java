package com.renault.garage.infrastructure.adapter.out.persistence.jpa.adapter;

import com.renault.garage.domain.model.Accessory;
import com.renault.garage.domain.model.valueobjects.AccessoryId;
import com.renault.garage.domain.model.valueobjects.VehicleId;
import com.renault.garage.domain.port.out.persistence.AccessoryRepository;
import com.renault.garage.infrastructure.adapter.out.persistence.jpa.entity.AccessoryEntity;
import com.renault.garage.infrastructure.adapter.out.persistence.jpa.entity.VehicleEntity;
import com.renault.garage.infrastructure.adapter.out.persistence.jpa.repository.AccessoryJpaRepository;
import com.renault.garage.infrastructure.adapter.out.persistence.jpa.repository.VehicleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccessoryJpaAdapter implements AccessoryRepository {

    private final AccessoryJpaRepository accessoryJpaRepository;
    private final VehicleJpaRepository vehicleJpaRepository;

    @Override
    public Accessory save(Accessory accessory) {
        VehicleEntity vehicleEntity = vehicleJpaRepository.findById(accessory.getVehicleId().getValue())
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));

        AccessoryEntity entity = toEntity(accessory, vehicleEntity);
        AccessoryEntity saved = accessoryJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Accessory> findById(AccessoryId id) {
        return accessoryJpaRepository.findById(id.getValue())
                .map(this::toDomain);
    }

    @Override
    public List<Accessory> findByVehicleId(VehicleId vehicleId) {
        return accessoryJpaRepository.findByVehicleId(vehicleId.getValue()).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(AccessoryId id) {
        accessoryJpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsById(AccessoryId id) {
        return accessoryJpaRepository.existsById(id.getValue());
    }

    private AccessoryEntity toEntity(Accessory accessory, VehicleEntity vehicle) {
        return AccessoryEntity.builder()
                .id(accessory.getId().getValue())
                .name(accessory.getName())
                .description(accessory.getDescription())
                .price(accessory.getPrice())
                .type(accessory.getType())
                .vehicle(vehicle)
                .build();
    }

    private Accessory toDomain(AccessoryEntity entity) {
        return Accessory.builder()
                .id(AccessoryId.of(entity.getId()))
                .name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .type(entity.getType())
                .vehicleId(VehicleId.of(entity.getVehicle().getId()))
                .build();
    }
}
