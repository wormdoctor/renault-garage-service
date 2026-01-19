package com.renault.garage.infrastructure.adapter.in.rest.mapper;

import com.renault.garage.domain.model.Garage;
import com.renault.garage.domain.model.OpeningTime;
import com.renault.garage.domain.model.valueobjects.GarageId;
import com.renault.garage.domain.port.in.garage.CreateGarageCommand;
import com.renault.garage.domain.port.in.garage.UpdateGarageCommand;
import com.renault.garage.infrastructure.adapter.in.rest.dto.request.CreateGarageRequest;
import com.renault.garage.infrastructure.adapter.in.rest.dto.request.OpeningTimeDto;
import com.renault.garage.infrastructure.adapter.in.rest.dto.request.UpdateGarageRequest;
import com.renault.garage.infrastructure.adapter.in.rest.dto.response.GarageResponse;
import com.renault.garage.infrastructure.adapter.in.rest.dto.response.OpeningTimeResponse;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class GarageRestMapper {

    public CreateGarageCommand toCommand(CreateGarageRequest request) {
        return CreateGarageCommand.builder()
            .name(request.getName())
            .address(request.getAddress())
            .telephone(request.getTelephone())
            .email(request.getEmail())
            .openingHours(toOpeningHoursDomain(request.getOpeningHours()))
            .build();
    }

    public UpdateGarageCommand toUpdateCommand(GarageId garageId, UpdateGarageRequest request) {
        return UpdateGarageCommand.builder()
            .garageId(garageId)
            .name(request.getName())
            .address(request.getAddress())
            .telephone(request.getTelephone())
            .email(request.getEmail())
            .openingHours(toOpeningHoursDomain(request.getOpeningHours()))
            .build();
    }

    public GarageResponse toResponse(Garage garage) {
        return GarageResponse.builder()
            .id(garage.getId().getValue())
            .name(garage.getName())
            .address(garage.getAddress())
            .telephone(garage.getTelephone())
            .email(garage.getEmail())
            .openingHours(toOpeningHoursResponse(garage.getOpeningHours()))
            .availableCapacity(garage.getAvailableCapacity())
            .build();
    }

    // Public method for controller to use
    public Map<DayOfWeek, List<OpeningTime>> toOpeningHoursDomain(
        Map<DayOfWeek, List<OpeningTimeDto>> dtos) {
        
        if (dtos == null) {
            return Map.of();
        }
        
        return dtos.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().stream()
                    .map(dto -> new OpeningTime(dto.getStartTime(), dto.getEndTime()))
                    .collect(Collectors.toList())
            ));
    }

    // Public method for converting domain to response
    public Map<DayOfWeek, List<OpeningTimeResponse>> toOpeningHoursResponse(
        Map<DayOfWeek, List<OpeningTime>> openingHours) {
        
        if (openingHours == null) {
            return Map.of();
        }
        
        return openingHours.entrySet().stream()
            .collect(Collectors.toMap(
                Map.Entry::getKey,
                e -> e.getValue().stream()
                    .map(ot -> new OpeningTimeResponse(ot.getStartTime(), ot.getEndTime()))
                    .collect(Collectors.toList())
            ));
    }
}
