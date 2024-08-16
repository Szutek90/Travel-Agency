package com.app.model.reservation;

import java.util.function.ToIntFunction;

public interface ReservationMapper {
    ToIntFunction<Reservation> toId = r -> r.id;
    ToIntFunction<Reservation> toTourId = r -> r.tourId;
    ToIntFunction<Reservation> toAgencyId = r -> r.agencyId;
    ToIntFunction<Reservation> toQuantityOfPeople = r -> r.quantityOfPeople;
}
