package com.renault.garage.infrastructure.adapter.out.persistence.jpa.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;
import java.util.*;

@Entity
@Table(name = "garages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GarageEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 500)
    private String address;

    @Column(nullable = false)
    private String telephone;

    @Column(nullable = false)
    private String email;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "garage_opening_hours", joinColumns = @JoinColumn(name = "garage_id"))
    @MapKeyEnumerated(EnumType.STRING)
    @MapKeyColumn(name = "day_of_week")
    @Column(name = "opening_times_json", columnDefinition = "TEXT")
    private Map<DayOfWeek, String> openingHoursJson = new HashMap<>();

    @OneToMany(mappedBy = "garage", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<VehicleEntity> vehicles = new ArrayList<>();
}
