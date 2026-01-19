package com.renault.garage.infrastructure.adapter.out.persistence.jpa.adapter;

import com.renault.garage.domain.model.Vehicle;
import com.renault.garage.domain.model.valueobjects.GarageId;
import com.renault.garage.domain.model.valueobjects.VehicleId;
import com.renault.garage.domain.port.out.persistence.VehicleRepository;
import com.renault.garage.infrastructure.adapter.out.persistence.jpa.entity.GarageEntity;
import com.renault.garage.infrastructure.adapter.out.persistence.jpa.entity.VehicleEntity;
import com.renault.garage.infrastructure.adapter.out.persistence.jpa.repository.GarageJpaRepository;
import com.renault.garage.infrastructure.adapter.out.persistence.jpa.repository.VehicleJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class VehicleJpaAdapter implements VehicleRepository {

    private final VehicleJpaRepository vehicleJpaRepository;
    private final GarageJpaRepository garageJpaRepository;

    @Override
    public Vehicle save(Vehicle vehicle) {
        GarageEntity garageEntity = garageJpaRepository.findById(vehicle.getGarageId().getValue())
                .orElseThrow(() -> new RuntimeException("Garage not found"));

        VehicleEntity entity = toEntity(vehicle, garageEntity);
        VehicleEntity saved = vehicleJpaRepository.save(entity);
        return toDomain(saved);
    }

    @Override
    public Optional<Vehicle> findById(VehicleId id) {
        return vehicleJpaRepository.findById(id.getValue())
                .map(this::toDomain);
    }

    @Override
    public List<Vehicle> findByGarageId(GarageId garageId) {
        return vehicleJpaRepository.findByGarageId(garageId.getValue()).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Vehicle> findByModel(String model) {
        return vehicleJpaRepository.findByModel(model).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(VehicleId id) {
        vehicleJpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsById(VehicleId id) {
        return vehicleJpaRepository.existsById(id.getValue());
    }

    private VehicleEntity toEntity(Vehicle vehicle, GarageEntity garage) {
        return VehicleEntity.builder()
                .id(vehicle.getId().getValue())
                .brand(vehicle.getBrand())
                .yearOfManufacture(vehicle.getYearOfManufacture())
                .fuelType(vehicle.getFuelType())
                .model(vehicle.getModel())
                .garage(garage)
                .build();
    }

    private Vehicle toDomain(VehicleEntity entity) {
        return Vehicle.builder()
                .id(VehicleId.of(entity.getId()))
                .brand(entity.getBrand())
                .yearOfManufacture(entity.getYearOfManufacture())
                .fuelType(entity.getFuelType())
                .model(entity.getModel())
                .garageId(GarageId.of(entity.getGarage().getId()))
                .accessories(new ArrayList<>())
                .build();
    }
}
