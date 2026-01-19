package com.renault.garage.domain.port.in.garage;

import com.renault.garage.domain.model.Garage;

public interface CreateGarageUseCase {
    Garage create(CreateGarageCommand command);
}
