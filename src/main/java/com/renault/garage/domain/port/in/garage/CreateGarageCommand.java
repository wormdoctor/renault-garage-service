package com.renault.garage.domain.port.in.garage;

import com.renault.garage.domain.model.OpeningTime;
import lombok.Builder;
import lombok.Value;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Map;

@Value
@Builder
public class CreateGarageCommand {
    String name;
    String address;
    String telephone;
    String email;
    Map<DayOfWeek, List<OpeningTime>> openingHours;
}
