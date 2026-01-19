package com.renault.garage.application.service.accessory;

import com.renault.garage.domain.exception.AccessoryNotFoundException;
import com.renault.garage.domain.exception.VehicleNotFoundException;
import com.renault.garage.domain.model.Accessory;
import com.renault.garage.domain.model.Vehicle;
import com.renault.garage.domain.model.valueobjects.AccessoryId;
import com.renault.garage.domain.port.in.accessory.DeleteAccessoryUseCase;
import com.renault.garage.domain.port.out.persistence.AccessoryRepository;
import com.renault.garage.domain.port.out.persistence.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DeleteAccessoryUseCaseImpl implements DeleteAccessoryUseCase {

    private final AccessoryRepository accessoryRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public void delete(AccessoryId accessoryId) {
        log.info("Deleting accessory: {}", accessoryId);

        // Verify accessory exists
        Accessory accessory = accessoryRepository.findById(accessoryId)
                .orElseThrow(() -> new AccessoryNotFoundException(accessoryId));

        // Find and update vehicle
        Vehicle vehicle = vehicleRepository.findById(accessory.getVehicleId())
                .orElseThrow(() -> new VehicleNotFoundException(accessory.getVehicleId()));

        vehicle.removeAccessory(accessoryId);

        // Delete accessory
        accessoryRepository.deleteById(accessoryId);

        log.info("Accessory deleted successfully: {}", accessoryId);
    }
}
