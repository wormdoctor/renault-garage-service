package com.renault.garage.domain.port.out.persistence;

import com.renault.garage.domain.model.Garage;
import com.renault.garage.domain.model.valueobjects.GarageId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface GarageRepository {
    Garage save(Garage garage);

    Optional<Garage> findById(GarageId id);

    Page<Garage> findAll(Pageable pageable);

    void deleteById(GarageId id);

    Page<Garage> findByVehicleType(String vehicleType, Pageable pageable);

    Page<Garage> findByAccessory(String accessoryName, Pageable pageable);

    boolean existsById(GarageId id);
}
