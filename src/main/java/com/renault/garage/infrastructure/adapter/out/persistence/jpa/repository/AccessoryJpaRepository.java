package com.renault.garage.infrastructure.adapter.out.persistence.jpa.repository;

import com.renault.garage.infrastructure.adapter.out.persistence.jpa.entity.AccessoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface AccessoryJpaRepository extends JpaRepository<AccessoryEntity, UUID> {

    @Query("SELECT a FROM AccessoryEntity a WHERE a.vehicle.id = :vehicleId")
    List<AccessoryEntity> findByVehicleId(@Param("vehicleId") UUID vehicleId);
}
