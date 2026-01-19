package com.renault.garage.domain.port.in.garage;

import com.renault.garage.domain.model.Garage;
import com.renault.garage.domain.model.valueobjects.GarageId;

public interface GetGarageUseCase {
    Garage findById(GarageId garageId);
}
