package com.renault.garage.domain.port.in.vehicle;

import com.renault.garage.domain.model.valueobjects.VehicleId;

public interface DeleteVehicleUseCase {
    void delete(VehicleId vehicleId);
}
