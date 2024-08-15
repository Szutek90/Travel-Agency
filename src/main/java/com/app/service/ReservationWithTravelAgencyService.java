package com.app.service;

import com.app.model.agency.TravelAgency;

import java.util.List;

public interface ReservationWithTravelAgencyService {
    List<TravelAgency> getAgencyWIthMostOrganizedTrips();
}
