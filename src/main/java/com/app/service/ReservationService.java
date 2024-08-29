package com.app.service;

import com.app.dto.reservation.GetReservationDto;
import com.app.dto.reservation.CreateReservationDto;
import com.app.model.TourWithClosestAvgPriceByAgency;
import com.app.model.agency.TravelAgency;
import com.app.model.country.Country;
import com.app.model.reservation.Reservation;
import com.app.model.tour.Tour;

import java.util.List;
import java.util.Map;

public interface ReservationService {
    void makeReservation(CreateReservationDto createReservationDto);

    void deleteReservation(int id);
    List<GetReservationDto> getAllReservations();

    List<TravelAgency> getAgencyWithMostOrganizedTrips();

    List<TravelAgency> getAgencyEarnMostMoney();

    List<Country> getMostVisitedCountries();

    Map<TravelAgency, TourWithClosestAvgPriceByAgency> getSummaryByTourAvgPrice();

    List<Tour> getToursTakingPlaceInGivenCountry(List<String> countryNames);
}
