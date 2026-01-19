package com.renault.garage.application.service.vehicle;

import com.renault.garage.domain.exception.VehicleNotFoundException;
import com.renault.garage.domain.model.Vehicle;
import com.renault.garage.domain.port.in.vehicle.UpdateVehicleCommand;
import com.renault.garage.domain.port.in.vehicle.UpdateVehicleUseCase;
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
public class UpdateVehicleUseCaseImpl implements UpdateVehicleUseCase {

    private final VehicleRepository vehicleRepository;
    private final VehicleEventPublisher eventPublisher;

    @Override
    public Vehicle update(UpdateVehicleCommand command) {
        log.info("Updating vehicle: {}", command.getVehicleId());

        // Find vehicle
        Vehicle vehicle = vehicleRepository.findById(command.getVehicleId())
                .orElseThrow(() -> new VehicleNotFoundException(command.getVehicleId()));

        // Update details
        vehicle.updateDetails(
                command.getBrand(),
                command.getYearOfManufacture(),
                command.getFuelType(),
                command.getModel());

        // Save
        Vehicle updatedVehicle = vehicleRepository.save(vehicle);

        // Publish event
        eventPublisher.publishVehicleUpdated(updatedVehicle);

        log.info("Vehicle updated successfully: {}", updatedVehicle.getId());
        return updatedVehicle;
    }
}
