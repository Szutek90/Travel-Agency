package com.app.model.agency;

import com.app.dto.travel_agency.GetTravelAgencyDto;
import lombok.*;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class TravelAgency {
    final Integer id;
    final String name;
    final String city;
    private final String phoneNumber;

    public GetTravelAgencyDto toGetTravelAgencyDto() {
        return new GetTravelAgencyDto(name, city, phoneNumber);
    }
}
