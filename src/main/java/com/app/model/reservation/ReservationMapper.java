package com.app.model.reservation;

import java.util.function.ToIntFunction;

public interface ReservationMapper {
    ToIntFunction<Reservation> toId = r -> r.id;
    ToIntFunction<Reservation> toAgencyId = r -> r.agencyId;
}
