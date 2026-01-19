package com.renault.garage.domain.port.in.accessory;

import com.renault.garage.domain.model.Accessory;

public interface UpdateAccessoryUseCase {
    Accessory update(UpdateAccessoryCommand command);
}
