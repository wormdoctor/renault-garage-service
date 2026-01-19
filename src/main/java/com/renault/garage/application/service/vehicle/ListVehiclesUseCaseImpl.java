package com.renault.garage.application.service.vehicle;

import com.renault.garage.domain.exception.GarageNotFoundException;
import com.renault.garage.domain.model.Vehicle;
import com.renault.garage.domain.model.valueobjects.GarageId;
import com.renault.garage.domain.port.in.vehicle.ListVehiclesUseCase;
import com.renault.garage.domain.port.out.persistence.GarageRepository;
import com.renault.garage.domain.port.out.persistence.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ListVehiclesUseCaseImpl implements ListVehiclesUseCase {

    private final VehicleRepository vehicleRepository;
    private final GarageRepository garageRepository;

    @Override
    public List<Vehicle> listByGarage(GarageId garageId) {
        log.info("Listing vehicles for garage: {}", garageId);

        // Verify garage exists
        if (!garageRepository.existsById(garageId)) {
            throw new GarageNotFoundException(garageId);
        }

        return vehicleRepository.findByGarageId(garageId);
    }

    @Override
    public List<Vehicle> listByModel(String model) {
        log.info("Listing vehicles by model: {}", model);
        return vehicleRepository.findByModel(model);
    }
}
