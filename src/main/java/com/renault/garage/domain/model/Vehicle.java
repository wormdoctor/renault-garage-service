package com.renault.garage.domain.model;

import com.renault.garage.domain.model.valueobjects.AccessoryId;
import com.renault.garage.domain.model.valueobjects.GarageId;
import com.renault.garage.domain.model.valueobjects.VehicleId;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Vehicle {
    private final VehicleId id;
    private String brand;
    private Integer yearOfManufacture;
    private String fuelType;
    private String model;
    private final GarageId garageId;
    private final List<Accessory> accessories;
    
    // Constructor for builder pattern
    public Vehicle(VehicleId id, String brand, Integer yearOfManufacture, 
                   String fuelType, String model, GarageId garageId) {
        this.id = id;
        this.brand = brand;
        this.yearOfManufacture = yearOfManufacture;
        this.fuelType = fuelType;
        this.model = model;
        this.garageId = garageId;
        this.accessories = new ArrayList<>();
        validate();
    }
    
    // Builder
    public static VehicleBuilder builder() {
        return new VehicleBuilder();
    }
    
    public static class VehicleBuilder {
        private VehicleId id;
        private String brand;
        private Integer yearOfManufacture;
        private String fuelType;
        private String model;
        private GarageId garageId;
        private List<Accessory> accessories = new ArrayList<>();
        
        public VehicleBuilder id(VehicleId id) {
            this.id = id;
            return this;
        }
        
        public VehicleBuilder brand(String brand) {
            this.brand = brand;
            return this;
        }
        
        public VehicleBuilder yearOfManufacture(Integer yearOfManufacture) {
            this.yearOfManufacture = yearOfManufacture;
            return this;
        }
        
        public VehicleBuilder fuelType(String fuelType) {
            this.fuelType = fuelType;
            return this;
        }
        
        public VehicleBuilder model(String model) {
            this.model = model;
            return this;
        }
        
        public VehicleBuilder garageId(GarageId garageId) {
            this.garageId = garageId;
            return this;
        }
        
        public VehicleBuilder accessories(List<Accessory> accessories) {
            this.accessories = accessories != null ? accessories : new ArrayList<>();
            return this;
        }
        
        public Vehicle build() {
            Vehicle vehicle = new Vehicle(id, brand, yearOfManufacture, fuelType, model, garageId);
            vehicle.accessories.addAll(accessories);
            return vehicle;
        }
    }
    
    public void validate() {
        if (brand == null || brand.isBlank()) {
            throw new IllegalArgumentException("Brand is required");
        }
        if (yearOfManufacture == null) {
            throw new IllegalArgumentException("Year of manufacture is required");
        }
        if (yearOfManufacture < 1900 || yearOfManufacture > 2100) {
            throw new IllegalArgumentException("Year of manufacture must be between 1900 and 2100");
        }
        if (fuelType == null || fuelType.isBlank()) {
            throw new IllegalArgumentException("Fuel type is required");
        }
    }
    
    // Update vehicle details
    public void updateDetails(String brand, Integer yearOfManufacture, String fuelType, String model) {
        this.brand = brand;
        this.yearOfManufacture = yearOfManufacture;
        this.fuelType = fuelType;
        this.model = model;
        validate();
    }
    
    // Add accessory
    public void addAccessory(Accessory accessory) {
        if (accessory == null) {
            throw new IllegalArgumentException("Accessory cannot be null");
        }
        this.accessories.add(accessory);
    }
    
    // Remove accessory
    public void removeAccessory(AccessoryId accessoryId) {
        if (accessoryId == null) {
            throw new IllegalArgumentException("Accessory ID cannot be null");
        }
        this.accessories.removeIf(acc -> acc.getId().equals(accessoryId));
    }
    
    // Get accessories (defensive copy)
    public List<Accessory> getAccessories() {
        return new ArrayList<>(accessories);
    }
}
