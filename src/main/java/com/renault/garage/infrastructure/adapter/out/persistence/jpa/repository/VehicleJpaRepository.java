package com.renault.garage.infrastructure.adapter.out.persistence.jpa.repository;

import com.renault.garage.infrastructure.adapter.out.persistence.jpa.entity.VehicleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface VehicleJpaRepository extends JpaRepository<VehicleEntity, UUID> {

    @Query("SELECT v FROM VehicleEntity v WHERE v.garage.id = :garageId")
    List<VehicleEntity> findByGarageId(@Param("garageId") UUID garageId);

    List<VehicleEntity> findByModel(String model);
}
