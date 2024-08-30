package com.app.service.impl;

import com.app.dto.travel_agency.CreateTravelAgencyDto;
import com.app.dto.travel_agency.GetTravelAgencyDto;
import com.app.model.agency.TravelAgency;
import com.app.model.agency.TravelAgencyMapper;
import com.app.repository.TravelAgencyRepository;
import com.app.service.TravelAgencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class TravelAgencyServiceImpl implements TravelAgencyService {
    private final TravelAgencyRepository travelAgencyRepository;

    public List<GetTravelAgencyDto> getAllTravelAgency() {
        return travelAgencyRepository.getAll().stream()
                .map(TravelAgency::toGetTravelAgencyDto)
                .toList();
    }

    @Override
    public GetTravelAgencyDto getTravelAgencyById(int id) {
        return travelAgencyRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("There is no Travel Agency with given id"))
                .toGetTravelAgencyDto();
    }

    @Override
    public GetTravelAgencyDto getTravelAgencyByName(String name) {
        return travelAgencyRepository.findByName(name)
                .orElseThrow(() ->
                        new NoSuchElementException("There is no Travel Agency with given id"))
                .toGetTravelAgencyDto();
    }

    @Override
    public List<GetTravelAgencyDto> getAllTravelAgenciesByCity(String city) {
        return travelAgencyRepository.findByCity(city).stream()
                .map(TravelAgency::toGetTravelAgencyDto)
                .toList();
    }

    @Override
    public GetTravelAgencyDto addTravelAgency(CreateTravelAgencyDto travelAgencyDto) {
        if (travelAgencyRepository.findByName(travelAgencyDto.name()).isPresent()) {
            throw new IllegalArgumentException("There is already a Travel Agency with given name");
        }
        var agencyToSave =
                new TravelAgency(getLastFreeId(), travelAgencyDto.name(), travelAgencyDto.city(),
                        travelAgencyDto.phoneNumber());
        travelAgencyRepository.save(agencyToSave);
        return agencyToSave.toGetTravelAgencyDto();
    }

    private int getLastFreeId() {
        return TravelAgencyMapper.toId.applyAsInt(travelAgencyRepository.getAll().getLast()) + 1;
    }
}
