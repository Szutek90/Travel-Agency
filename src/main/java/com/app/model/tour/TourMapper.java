package com.app.model.tour;

import java.math.BigDecimal;
import java.util.function.Function;
import java.util.function.ToIntFunction;

public interface TourMapper {
    ToIntFunction<Tour> toId = t -> t.id;
    ToIntFunction<Tour> toAgencyId = t -> t.agencyId;
    ToIntFunction<Tour> toCountryId = t -> t.countryId;
    Function<Tour, BigDecimal> toPrice = t -> t.pricePerPerson;
}
