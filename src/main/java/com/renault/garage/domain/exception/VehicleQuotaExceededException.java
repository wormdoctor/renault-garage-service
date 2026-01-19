package com.renault.garage.domain.exception;

import com.renault.garage.domain.model.valueobjects.GarageId;

public class VehicleQuotaExceededException extends BusinessException {
    public VehicleQuotaExceededException(GarageId garageId) {
        super("Garage " + garageId.getValue() + " has reached maximum capacity of 50 vehicles");
    }
    
    public VehicleQuotaExceededException(String message) {
        super(message);
    }
}
