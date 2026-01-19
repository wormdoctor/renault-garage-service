package com.renault.garage.domain.port.out.persistence;

import com.renault.garage.domain.model.Accessory;
import com.renault.garage.domain.model.valueobjects.AccessoryId;
import com.renault.garage.domain.model.valueobjects.VehicleId;

import java.util.List;
import java.util.Optional;

public interface AccessoryRepository {
    Accessory save(Accessory accessory);

    Optional<Accessory> findById(AccessoryId id);

    List<Accessory> findByVehicleId(VehicleId vehicleId);

    void deleteById(AccessoryId id);

    boolean existsById(AccessoryId id);
}
