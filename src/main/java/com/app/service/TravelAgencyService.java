package com.app.service;

import com.app.dto.travel_agency.CreateTravelAgencyDto;
import com.app.dto.travel_agency.GetTravelAgencyDto;
import com.app.model.agency.TravelAgency;

import java.util.List;

public interface TravelAgencyService {
    public List<GetTravelAgencyDto> getAllTravelAgency();
    GetTravelAgencyDto getTravelAgencyById(int id);

    GetTravelAgencyDto getTravelAgencyByName(String name);

    List<GetTravelAgencyDto> getAllTravelAgenciesByCity(String city);

    GetTravelAgencyDto addTravelAgency(CreateTravelAgencyDto travelAgencyDto);
}
