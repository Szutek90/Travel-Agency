package com.app.service;

import com.app.dto.ReservationDto;
import com.app.model.agency.TravelAgency;
import com.app.model.country.Country;
import com.app.model.reservation.Reservation;

import java.util.List;

public interface ReservationWIthTourPersonAgencyService {
    void makeReservation(ReservationDto reservationDto);

    void deleteReservation(Reservation reservation);

    List<TravelAgency> getAgencyWithMostOrganizedTrips();

    List<TravelAgency> getAgencyEarnMostMoney();

    List<Country> getMostVisitedCountries();
}
