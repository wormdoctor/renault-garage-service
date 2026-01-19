package com.renault.garage.application.service.garage;

import com.renault.garage.domain.exception.GarageNotFoundException;
import com.renault.garage.domain.model.valueobjects.GarageId;
import com.renault.garage.domain.port.in.garage.DeleteGarageUseCase;
import com.renault.garage.domain.port.out.persistence.GarageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DeleteGarageUseCaseImpl implements DeleteGarageUseCase {

    private final GarageRepository garageRepository;

    @Override
    public void delete(GarageId garageId) {
        log.info("Deleting garage: {}", garageId);

        if (!garageRepository.existsById(garageId)) {
            throw new GarageNotFoundException("Garage not found with id: " + garageId);
        }

        garageRepository.deleteById(garageId);
        log.info("Garage deleted successfully: {}", garageId);
    }
}
