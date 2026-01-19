package com.renault.garage.domain.port.out.persistence;

import com.renault.garage.domain.model.Vehicle;
import com.renault.garage.domain.model.valueobjects.GarageId;
import com.renault.garage.domain.model.valueobjects.VehicleId;

import java.util.List;
import java.util.Optional;

public interface VehicleRepository {
    Vehicle save(Vehicle vehicle);

    Optional<Vehicle> findById(VehicleId id);

    List<Vehicle> findByGarageId(GarageId garageId);

    List<Vehicle> findByModel(String model);

    void deleteById(VehicleId id);

    boolean existsById(VehicleId id);
}
