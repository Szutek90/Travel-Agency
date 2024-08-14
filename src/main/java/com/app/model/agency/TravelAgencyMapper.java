package com.app.model.agency;

import java.util.function.Function;

public interface TravelAgencyMapper {
    Function<TravelAgency, Integer> toId = travelAgency -> travelAgency.id;
    Function<TravelAgency, String> toName = travelAgency -> travelAgency.name;
    Function<TravelAgency, String> toCity = travelAgency -> travelAgency.city;
}
