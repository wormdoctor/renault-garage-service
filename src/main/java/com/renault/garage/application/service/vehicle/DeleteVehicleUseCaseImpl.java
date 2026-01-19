package com.renault.garage.application.service.vehicle;

import com.renault.garage.domain.exception.VehicleNotFoundException;
import com.renault.garage.domain.model.Vehicle;
import com.renault.garage.domain.model.valueobjects.VehicleId;
import com.renault.garage.domain.port.in.vehicle.DeleteVehicleUseCase;
import com.renault.garage.domain.port.out.messaging.VehicleEventPublisher;
import com.renault.garage.domain.port.out.persistence.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DeleteVehicleUseCaseImpl implements DeleteVehicleUseCase {

    private final VehicleRepository vehicleRepository;
    private final VehicleEventPublisher eventPublisher;

    @Override
    public void delete(VehicleId vehicleId) {
        log.info("Deleting vehicle: {}", vehicleId);

        // Verify vehicle exists
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId));

        // Delete
        vehicleRepository.deleteById(vehicleId);

        // Publish event
        eventPublisher.publishVehicleDeleted(vehicle);

        log.info("Vehicle deleted successfully: {}", vehicleId);
    }
}
