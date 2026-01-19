package com.renault.garage.domain.model;

import com.renault.garage.domain.exception.VehicleQuotaExceededException;
import com.renault.garage.domain.model.valueobjects.GarageId;
import lombok.Getter;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Garage {
    private static final int MAX_VEHICLES = 50;
    
    private final GarageId id;
    private String name;
    private String address;
    private String telephone;
    private String email;
    private Map<DayOfWeek, List<OpeningTime>> openingHours;
    private final List<Vehicle> vehicles;
    
    public Garage(GarageId id, String name, String address, String telephone, 
                  String email, Map<DayOfWeek, List<OpeningTime>> openingHours) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.openingHours = openingHours != null ? openingHours : new HashMap<>();
        this.vehicles = new ArrayList<>();
        validate();
    }
    
    public static GarageBuilder builder() {
        return new GarageBuilder();
    }
    
    public static class GarageBuilder {
        private GarageId id;
        private String name;
        private String address;
        private String telephone;
        private String email;
        private Map<DayOfWeek, List<OpeningTime>> openingHours = new HashMap<>();
        private List<Vehicle> vehicles = new ArrayList<>();
        
        public GarageBuilder id(GarageId id) {
            this.id = id;
            return this;
        }
        
        public GarageBuilder name(String name) {
            this.name = name;
            return this;
        }
        
        public GarageBuilder address(String address) {
            this.address = address;
            return this;
        }
        
        public GarageBuilder telephone(String telephone) {
            this.telephone = telephone;
            return this;
        }
        
        public GarageBuilder email(String email) {
            this.email = email;
            return this;
        }
        
        public GarageBuilder openingHours(Map<DayOfWeek, List<OpeningTime>> openingHours) {
            this.openingHours = openingHours != null ? openingHours : new HashMap<>();
            return this;
        }
        
        public GarageBuilder vehicles(List<Vehicle> vehicles) {
            this.vehicles = vehicles != null ? vehicles : new ArrayList<>();
            return this;
        }
        
        public Garage build() {
            Garage garage = new Garage(id, name, address, telephone, email, openingHours);
            garage.vehicles.addAll(vehicles);
            return garage;
        }
    }
    
    public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (address == null || address.isBlank()) {
            throw new IllegalArgumentException("Address is required");
        }
        if (telephone == null || telephone.isBlank()) {
            throw new IllegalArgumentException("Telephone is required");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
    }
    
    public void updateDetails(String name, String address, String telephone, 
                            String email, Map<DayOfWeek, List<OpeningTime>> openingHours) {
        this.name = name;
        this.address = address;
        this.telephone = telephone;
        this.email = email;
        this.openingHours = openingHours != null ? openingHours : new HashMap<>();
        validate();
    }
    
    public void addVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        if (vehicles.size() >= MAX_VEHICLES) {
            throw new VehicleQuotaExceededException(this.id);
        }
        vehicles.add(vehicle);
    }
    
    public void removeVehicle(Vehicle vehicle) {
        if (vehicle == null) {
            throw new IllegalArgumentException("Vehicle cannot be null");
        }
        vehicles.remove(vehicle);
    }
    
    public Integer getAvailableCapacity() {
        return MAX_VEHICLES - vehicles.size();
    }
    
    public List<Vehicle> getVehicles() {
        return new ArrayList<>(vehicles);
    }
}
