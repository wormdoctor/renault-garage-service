package com.renault.garage.application.service.vehicle;

import com.renault.garage.domain.exception.GarageNotFoundException;
import com.renault.garage.domain.model.Garage;
import com.renault.garage.domain.model.Vehicle;
import com.renault.garage.domain.model.valueobjects.VehicleId;
import com.renault.garage.domain.port.in.vehicle.AddVehicleCommand;
import com.renault.garage.domain.port.in.vehicle.AddVehicleUseCase;
import com.renault.garage.domain.port.out.messaging.VehicleEventPublisher;
import com.renault.garage.domain.port.out.persistence.GarageRepository;
import com.renault.garage.domain.port.out.persistence.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AddVehicleUseCaseImpl implements AddVehicleUseCase {

    private final VehicleRepository vehicleRepository;
    private final GarageRepository garageRepository;
    private final VehicleEventPublisher eventPublisher;

    @Override
    public Vehicle add(AddVehicleCommand command) {
        log.info("Adding vehicle to garage: {}", command.getGarageId());

        // Verify garage exists
        Garage garage = garageRepository.findById(command.getGarageId())
                .orElseThrow(() -> new GarageNotFoundException(command.getGarageId()));

        // Check garage capacity
        if (garage.getAvailableCapacity() <= 0) {
            throw new com.renault.garage.domain.exception.VehicleQuotaExceededException(garage.getId());
        }

        // Create vehicle
        Vehicle vehicle = Vehicle.builder()
                .id(VehicleId.generate())
                .brand(command.getBrand())
                .yearOfManufacture(command.getYearOfManufacture())
                .fuelType(command.getFuelType())
                .model(command.getModel())
                .garageId(command.getGarageId())
                .build();

        // Save vehicle
        Vehicle savedVehicle = vehicleRepository.save(vehicle);

        // Publish event
        eventPublisher.publishVehicleCreated(savedVehicle);

        log.info("Vehicle added successfully: {}", savedVehicle.getId());
        return savedVehicle;
    }
}
