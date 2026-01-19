package com.renault.garage.domain.port.in.garage;

import com.renault.garage.domain.model.Garage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SearchGarageUseCase {
    Page<Garage> findAll(Pageable pageable);
    Page<Garage> findByVehicleType(String vehicleType, Pageable pageable);
    Page<Garage> findByAccessory(String accessoryName, Pageable pageable);
}
