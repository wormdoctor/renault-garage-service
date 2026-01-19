package com.renault.garage.domain.exception;

import com.renault.garage.domain.model.valueobjects.AccessoryId;

public class AccessoryNotFoundException extends BusinessException {
    public AccessoryNotFoundException(AccessoryId accessoryId) {
        super("Accessory not found with id: " + accessoryId.getValue());
    }
    
    public AccessoryNotFoundException(String message) {
        super(message);
    }
}
