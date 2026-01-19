package com.renault.garage.application.service.garage;

import com.renault.garage.domain.model.Garage;
import com.renault.garage.domain.port.in.garage.SearchGarageUseCase;
import com.renault.garage.domain.port.out.persistence.GarageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class SearchGarageUseCaseImpl implements SearchGarageUseCase {

    private final GarageRepository garageRepository;

    @Override
    public Page<Garage> findAll(Pageable pageable) {
        log.debug("Finding all garages with pageable: {}", pageable);
        return garageRepository.findAll(pageable);
    }

    @Override
    public Page<Garage> findByVehicleType(String vehicleType, Pageable pageable) {
        log.debug("Finding garages by vehicle type: {}", vehicleType);
        return garageRepository.findByVehicleType(vehicleType, pageable);
    }

    @Override
    public Page<Garage> findByAccessory(String accessoryName, Pageable pageable) {
        log.debug("Finding garages by accessory: {}", accessoryName);
        return garageRepository.findByAccessory(accessoryName, pageable);
    }
}
