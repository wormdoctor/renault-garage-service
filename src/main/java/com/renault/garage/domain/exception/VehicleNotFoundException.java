package com.renault.garage.domain.exception;

import com.renault.garage.domain.model.valueobjects.VehicleId;

public class VehicleNotFoundException extends BusinessException {
    public VehicleNotFoundException(VehicleId vehicleId) {
        super("Vehicle not found with id: " + vehicleId.getValue());
    }
    
    public VehicleNotFoundException(String message) {
        super(message);
    }
}
