package com.app.model.agency;

import java.util.function.Function;
import java.util.function.ToIntFunction;

public interface TravelAgencyMapper {
    ToIntFunction<TravelAgency> toId = travelAgency -> travelAgency.id;
    Function<TravelAgency, String> toName = travelAgency -> travelAgency.name;
    Function<TravelAgency, String> toCity = travelAgency -> travelAgency.city;
}
