package com.renault.garage.domain.port.in.vehicle;

import com.renault.garage.domain.model.Vehicle;

public interface AddVehicleUseCase {
    Vehicle add(AddVehicleCommand command);
}
