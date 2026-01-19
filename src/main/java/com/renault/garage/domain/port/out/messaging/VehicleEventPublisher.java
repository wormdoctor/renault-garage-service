package com.renault.garage.domain.port.out.messaging;

import com.renault.garage.domain.model.Vehicle;

public interface VehicleEventPublisher {
    void publishVehicleCreated(Vehicle vehicle);

    void publishVehicleUpdated(Vehicle vehicle);

    void publishVehicleDeleted(Vehicle vehicle);
}
