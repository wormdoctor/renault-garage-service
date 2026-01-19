package com.renault.garage.application.service.accessory;

import com.renault.garage.domain.exception.VehicleNotFoundException;
import com.renault.garage.domain.model.Accessory;
import com.renault.garage.domain.model.Vehicle;
import com.renault.garage.domain.model.valueobjects.AccessoryId;
import com.renault.garage.domain.port.in.accessory.AddAccessoryCommand;
import com.renault.garage.domain.port.in.accessory.AddAccessoryUseCase;
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
public class AddAccessoryUseCaseImpl implements AddAccessoryUseCase {

    private final AccessoryRepository accessoryRepository;
    private final VehicleRepository vehicleRepository;

    @Override
    public Accessory add(AddAccessoryCommand command) {
        log.info("Adding accessory to vehicle: {}", command.getVehicleId());

        // Verify vehicle exists
        Vehicle vehicle = vehicleRepository.findById(command.getVehicleId())
                .orElseThrow(() -> new VehicleNotFoundException(command.getVehicleId()));

        // Create accessory
        Accessory accessory = Accessory.builder()
                .id(AccessoryId.generate())
                .name(command.getName())
                .description(command.getDescription())
                .price(command.getPrice())
                .type(command.getType())
                .vehicleId(command.getVehicleId())
                .build();

        accessory.validate();

        // Add to vehicle
        vehicle.addAccessory(accessory);

        // Save
        Accessory savedAccessory = accessoryRepository.save(accessory);

        log.info("Accessory added successfully: {}", savedAccessory.getId());
        return savedAccessory;
    }
}
