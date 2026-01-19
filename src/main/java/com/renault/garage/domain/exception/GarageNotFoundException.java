package com.renault.garage.domain.exception;

import com.renault.garage.domain.model.valueobjects.GarageId;

public class GarageNotFoundException extends BusinessException {
    public GarageNotFoundException(GarageId garageId) {
        super("Garage not found with id: " + garageId.getValue());
    }
    
    public GarageNotFoundException(String message) {
        super(message);
    }
}
