package com.renault.garage.domain.model;

import com.renault.garage.domain.model.valueobjects.AccessoryId;
import com.renault.garage.domain.model.valueobjects.VehicleId;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Accessory {
    private final AccessoryId id;
    private String name;
    private String description;
    private BigDecimal price;
    private String type;
    private final VehicleId vehicleId;
    
    public Accessory(AccessoryId id, String name, String description, 
                     BigDecimal price, String type, VehicleId vehicleId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        this.vehicleId = vehicleId;
        validate();
    }
    
    public static AccessoryBuilder builder() {
        return new AccessoryBuilder();
    }
    
    public static class AccessoryBuilder {
        private AccessoryId id;
        private String name;
        private String description;
        private BigDecimal price;
        private String type;
        private VehicleId vehicleId;
        
        public AccessoryBuilder id(AccessoryId id) {
            this.id = id;
            return this;
        }
        
        public AccessoryBuilder name(String name) {
            this.name = name;
            return this;
        }
        
        public AccessoryBuilder description(String description) {
            this.description = description;
            return this;
        }
        
        public AccessoryBuilder price(BigDecimal price) {
            this.price = price;
            return this;
        }
        
        public AccessoryBuilder type(String type) {
            this.type = type;
            return this;
        }
        
        public AccessoryBuilder vehicleId(VehicleId vehicleId) {
            this.vehicleId = vehicleId;
            return this;
        }
        
        public Accessory build() {
            return new Accessory(id, name, description, price, type, vehicleId);
        }
    }
    
    public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }
        if (description == null || description.isBlank()) {
            throw new IllegalArgumentException("Description is required");
        }
        if (price == null) {
            throw new IllegalArgumentException("Price is required");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Price must be positive or zero");
        }
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("Type is required");
        }
    }
    
    public void updateDetails(String name, String description, BigDecimal price, String type) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.type = type;
        validate();
    }
}
