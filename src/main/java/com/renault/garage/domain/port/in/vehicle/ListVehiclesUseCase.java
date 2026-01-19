package com.renault.garage.domain.port.in.vehicle;

import com.renault.garage.domain.model.Vehicle;
import com.renault.garage.domain.model.valueobjects.GarageId;

import java.util.List;

public interface ListVehiclesUseCase {
    List<Vehicle> listByGarage(GarageId garageId);
    List<Vehicle> listByModel(String model);
}
