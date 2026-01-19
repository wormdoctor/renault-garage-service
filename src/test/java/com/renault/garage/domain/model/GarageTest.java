package com.renault.garage.domain.model;

import com.renault.garage.domain.exception.VehicleQuotaExceededException;
import com.renault.garage.domain.model.valueobjects.GarageId;
import com.renault.garage.domain.model.valueobjects.VehicleId;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

class GarageTest {

    @Test
    void shouldCreateValidGarage() {
        // Given
        GarageId id = GarageId.generate();
        Map<DayOfWeek, List<OpeningTime>> openingHours = Map.of(
                DayOfWeek.MONDAY, List.of(new OpeningTime("09:00", "18:00")));

        // When
        Garage garage = Garage.builder()
                .id(id)
                .name("Test Garage")
                .address("123 Test Street")
                .telephone("+33123456789")
                .email("test@garage.fr")
                .openingHours(openingHours)
                .build();

        // Then
        assertThat(garage.getName()).isEqualTo("Test Garage");
        assertThat(garage.getAvailableCapacity()).isEqualTo(50);
    }

    @Test
    void shouldThrowExceptionWhenExceeding50Vehicles() {
        // Given
        GarageId garageId = GarageId.generate();
        Map<DayOfWeek, List<OpeningTime>> openingHours = Map.of(
                DayOfWeek.MONDAY, List.of(new OpeningTime("09:00", "18:00")));

        Garage garage = Garage.builder()
                .id(garageId)
                .name("Test Garage")
                .address("123 Test Street")
                .telephone("+33123456789")
                .email("test@garage.fr")
                .openingHours(openingHours)
                .build();

        // Add 50 vehicles
        for (int i = 0; i < 50; i++) {
            Vehicle vehicle = Vehicle.builder()
                    .id(VehicleId.generate())
                    .brand("Renault")
                    .yearOfManufacture(2024)
                    .fuelType("Electric")
                    .model("Zoe " + i)
                    .garageId(garageId)
                    .build();
            garage.addVehicle(vehicle);
        }

        // When/Then - Adding 51st vehicle should throw exception
        Vehicle extraVehicle = Vehicle.builder()
                .id(VehicleId.generate())
                .brand("Renault")
                .yearOfManufacture(2024)
                .fuelType("Electric")
                .model("Extra")
                .garageId(garageId)
                .build();

        assertThatThrownBy(() -> garage.addVehicle(extraVehicle))
                .isInstanceOf(VehicleQuotaExceededException.class)
                .hasMessageContaining("maximum capacity of 50 vehicles");
    }

    @Test
    void shouldUpdateGarageDetails() {
        // Given
        GarageId id = GarageId.generate();
        Map<DayOfWeek, List<OpeningTime>> openingHours = Map.of(
                DayOfWeek.MONDAY, List.of(new OpeningTime("09:00", "18:00")));

        Garage garage = Garage.builder()
                .id(id)
                .name("Old Name")
                .address("Old Address")
                .telephone("+33111111111")
                .email("old@garage.fr")
                .openingHours(openingHours)
                .build();

        // When
        Map<DayOfWeek, List<OpeningTime>> newOpeningHours = Map.of(
                DayOfWeek.MONDAY, List.of(new OpeningTime("08:00", "19:00")));

        garage.updateDetails(
                "New Name",
                "New Address",
                "+33222222222",
                "new@garage.fr",
                newOpeningHours);

        // Then
        assertThat(garage.getName()).isEqualTo("New Name");
        assertThat(garage.getAddress()).isEqualTo("New Address");
        assertThat(garage.getTelephone()).isEqualTo("+33222222222");
        assertThat(garage.getEmail()).isEqualTo("new@garage.fr");
    }

    @Test
    void shouldThrowExceptionForInvalidGarage() {
        // When/Then - Missing name
        assertThatThrownBy(() -> Garage.builder()
                .id(GarageId.generate())
                .name("")
                .address("Test Address")
                .telephone("+33123456789")
                .email("test@garage.fr")
                .openingHours(Map.of())
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Name is required");

        // When/Then - Missing email
        assertThatThrownBy(() -> Garage.builder()
                .id(GarageId.generate())
                .name("Test")
                .address("Test Address")
                .telephone("+33123456789")
                .email("")
                .openingHours(Map.of())
                .build())
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("Email is required");
    }
}
