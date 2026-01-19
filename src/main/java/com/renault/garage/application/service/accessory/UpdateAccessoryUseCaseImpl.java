package com.renault.garage.application.service.accessory;

import com.renault.garage.domain.exception.AccessoryNotFoundException;
import com.renault.garage.domain.model.Accessory;
import com.renault.garage.domain.port.in.accessory.UpdateAccessoryCommand;
import com.renault.garage.domain.port.in.accessory.UpdateAccessoryUseCase;
import com.renault.garage.domain.port.out.persistence.AccessoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UpdateAccessoryUseCaseImpl implements UpdateAccessoryUseCase {

    private final AccessoryRepository accessoryRepository;

    @Override
    public Accessory update(UpdateAccessoryCommand command) {
        log.info("Updating accessory: {}", command.getAccessoryId());

        // Find accessory
        Accessory accessory = accessoryRepository.findById(command.getAccessoryId())
                .orElseThrow(() -> new AccessoryNotFoundException(command.getAccessoryId()));

        // Update details
        accessory.updateDetails(
                command.getName(),
                command.getDescription(),
                command.getPrice(),
                command.getType());

        // Save
        Accessory updatedAccessory = accessoryRepository.save(accessory);

        log.info("Accessory updated successfully: {}", updatedAccessory.getId());
        return updatedAccessory;
    }
}
