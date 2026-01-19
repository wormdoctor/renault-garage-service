package com.renault.garage.application.service.garage;

import com.renault.garage.domain.exception.GarageNotFoundException;
import com.renault.garage.domain.model.Garage;
import com.renault.garage.domain.port.in.garage.UpdateGarageCommand;
import com.renault.garage.domain.port.in.garage.UpdateGarageUseCase;
import com.renault.garage.domain.port.out.persistence.GarageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class UpdateGarageUseCaseImpl implements UpdateGarageUseCase {

    private final GarageRepository garageRepository;

    @Override
    public Garage update(UpdateGarageCommand command) {
        log.info("Updating garage: {}", command.getGarageId());

        Garage garage = garageRepository.findById(command.getGarageId())
                .orElseThrow(() -> new GarageNotFoundException(
                        "Garage not found with id: " + command.getGarageId()));

        garage.updateDetails(
                command.getName(),
                command.getAddress(),
                command.getTelephone(),
                command.getEmail(),
                command.getOpeningHours());

        Garage updatedGarage = garageRepository.save(garage);
        log.info("Garage updated successfully: {}", updatedGarage.getId());

        return updatedGarage;
    }
}
