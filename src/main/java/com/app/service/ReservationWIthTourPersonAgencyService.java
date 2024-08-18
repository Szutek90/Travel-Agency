package com.app.service;

import com.app.dto.ReservationDto;
import com.app.model.TourWithClosestAvgPriceByAgency;
import com.app.model.agency.TravelAgency;
import com.app.model.country.Country;
import com.app.model.reservation.Reservation;
import com.app.model.tour.Tour;

import java.util.List;
import java.util.Map;

public interface ReservationWIthTourPersonAgencyService {
    void makeReservation(ReservationDto reservationDto);

    void deleteReservation(Reservation reservation);

    List<TravelAgency> getAgencyWithMostOrganizedTrips();

    List<TravelAgency> getAgencyEarnMostMoney();

    List<Country> getMostVisitedCountries();

    Map<TravelAgency, TourWithClosestAvgPriceByAgency> getSummaryByTourAvgPrice();

    List<Tour> getToursTakingPlaceInGivenCountry(List<String> countryNames);
}
