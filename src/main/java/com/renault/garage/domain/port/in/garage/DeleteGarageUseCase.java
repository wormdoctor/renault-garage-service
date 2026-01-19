package com.renault.garage.domain.port.in.garage;

import com.renault.garage.domain.model.valueobjects.GarageId;

public interface DeleteGarageUseCase {
    void delete(GarageId garageId);
}
