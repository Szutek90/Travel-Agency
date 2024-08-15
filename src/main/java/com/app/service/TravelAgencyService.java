package com.app.service;

import com.app.dto.TravelAgencyDto;
import com.app.model.agency.TravelAgency;

import java.util.List;

public interface TravelAgencyService {
    TravelAgency getTravelAgencyById(int id);

    TravelAgency getTravelAgencyByName(String name);

    List<TravelAgency> getAllTravelAgenciesByCity(String city);

    TravelAgency addTravelAgency(TravelAgencyDto travelAgencyDto);
}
