package com.renault.garage.domain.port.in.accessory;

import com.renault.garage.domain.model.valueobjects.AccessoryId;

public interface DeleteAccessoryUseCase {
    void delete(AccessoryId accessoryId);
}
