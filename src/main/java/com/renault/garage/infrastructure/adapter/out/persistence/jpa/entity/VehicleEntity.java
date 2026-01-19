package com.renault.garage.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "vehicles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false, name = "year_of_manufacture")
    private Integer yearOfManufacture;

    @Column(nullable = false, name = "fuel_type")
    private String fuelType;

    @Column(name = "model")
    private String model;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "garage_id", nullable = false)
    private GarageEntity garage;

    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AccessoryEntity> accessories = new ArrayList<>();
}
