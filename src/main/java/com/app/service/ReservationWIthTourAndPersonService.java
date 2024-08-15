package com.app.service;

import com.app.dto.ReservationDto;
import com.app.model.reservation.Reservation;

public interface ReservationWIthTourAndPersonService {
    void makeReservation(ReservationDto reservationDto);

    void deleteReservation(Reservation reservation);
}
