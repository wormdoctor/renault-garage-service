package com.renault.garage.infrastructure.adapter.out.persistence.jpa.repository;

import com.renault.garage.infrastructure.adapter.out.persistence.jpa.entity.GarageEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GarageJpaRepository extends JpaRepository<GarageEntity, UUID> {
    
    @Query("SELECT DISTINCT g FROM GarageEntity g " +
           "LEFT JOIN g.vehicles v " +
           "WHERE v.fuelType = :fuelType")
    Page<GarageEntity> findByVehiclesFuelType(@Param("fuelType") String fuelType, Pageable pageable);
    
    @Query("SELECT DISTINCT g FROM GarageEntity g " +
           "LEFT JOIN g.vehicles v " +
           "LEFT JOIN v.accessories a " +
           "WHERE LOWER(a.name) LIKE LOWER(CONCAT('%', :accessoryName, '%'))")
    Page<GarageEntity> findByVehiclesAccessoriesName(@Param("accessoryName") String accessoryName, Pageable pageable);
}
