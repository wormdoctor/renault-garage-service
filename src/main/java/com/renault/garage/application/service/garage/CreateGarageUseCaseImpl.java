package com.renault.garage.application.service.garage;

import com.renault.garage.domain.model.Garage;
import com.renault.garage.domain.model.valueobjects.GarageId;
import com.renault.garage.domain.port.in.garage.CreateGarageCommand;
import com.renault.garage.domain.port.in.garage.CreateGarageUseCase;
import com.renault.garage.domain.port.out.persistence.GarageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class CreateGarageUseCaseImpl implements CreateGarageUseCase {

    private final GarageRepository garageRepository;

    @Override
    public Garage create(CreateGarageCommand command) {
        log.info("Creating new garage: {}", command.getName());

        Garage garage = Garage.builder()
                .id(GarageId.generate())
                .name(command.getName())
                .address(command.getAddress())
                .telephone(command.getTelephone())
                .email(command.getEmail())
                .openingHours(command.getOpeningHours())
                .build();

        garage.validate();
        Garage savedGarage = garageRepository.save(garage);

        log.info("Garage created successfully with id: {}", savedGarage.getId());
        return savedGarage;
    }
}
