package com.app.service;

import com.app.dto.travel_agency.CreateTravelAgencyDto;
import com.app.dto.travel_agency.GetTravelAgencyDto;

import java.util.List;

public interface TravelAgencyService {
    List<GetTravelAgencyDto> getAllTravelAgency();
    GetTravelAgencyDto getTravelAgencyById(int id);

    GetTravelAgencyDto getTravelAgencyByName(String name);

    List<GetTravelAgencyDto> getAllTravelAgenciesByCity(String city);

    GetTravelAgencyDto addTravelAgency(CreateTravelAgencyDto travelAgencyDto);
}
