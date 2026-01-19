package com.renault.garage.application.service.garage;

import com.renault.garage.domain.exception.GarageNotFoundException;
import com.renault.garage.domain.model.Garage;
import com.renault.garage.domain.model.valueobjects.GarageId;
import com.renault.garage.domain.port.in.garage.GetGarageUseCase;
import com.renault.garage.domain.port.out.persistence.GarageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class GetGarageUseCaseImpl implements GetGarageUseCase {

    private final GarageRepository garageRepository;

    @Override
    public Garage findById(GarageId garageId) {
        log.debug("Finding garage by id: {}", garageId);

        return garageRepository.findById(garageId)
                .orElseThrow(() -> new GarageNotFoundException(
                        "Garage not found with id: " + garageId));
    }
}
