package com.renault.garage.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)  // Ignore extra fields from database
public class OpeningTime {
    private String startTime;  // Format: "HH:mm"
    private String endTime;    // Format: "HH:mm"
    
    public boolean isValid() {
        try {
            LocalTime start = LocalTime.parse(startTime);
            LocalTime end = LocalTime.parse(endTime);
            return start.isBefore(end);
        } catch (Exception e) {
            return false;
        }
    }
    
    public LocalTime getStartTimeAsLocalTime() {
        return LocalTime.parse(startTime);
    }
    
    public LocalTime getEndTimeAsLocalTime() {
        return LocalTime.parse(endTime);
    }
}
