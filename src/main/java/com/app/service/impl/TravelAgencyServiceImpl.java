package com.app.service.impl;

import com.app.dto.TravelAgencyDto;
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

    @Override
    public TravelAgency getTravelAgencyById(int id) {
        return travelAgencyRepository.findById(id)
                .orElseThrow(() ->
                        new NoSuchElementException("There is no Travel Agency with given id"));
    }

    @Override
    public TravelAgency getTravelAgencyByName(String name) {
        return travelAgencyRepository.findByName(name)
                .orElseThrow(() ->
                        new NoSuchElementException("There is no Travel Agency with given id"));
    }

    @Override
    public List<TravelAgency> getAllTravelAgenciesByCity(String city) {
        return travelAgencyRepository.findByCity(city);
    }

    @Override
    public TravelAgency addTravelAgency(TravelAgencyDto travelAgencyDto) {
        if (travelAgencyRepository.findByName(travelAgencyDto.name()).isPresent()) {
            throw new IllegalArgumentException("There is already a Travel Agency with given name");
        }
        var agencyToSave =
                new TravelAgency(getLastFreeId(), travelAgencyDto.name(), travelAgencyDto.city(),
                        travelAgencyDto.phoneNumber());
        travelAgencyRepository.save(agencyToSave);
        return agencyToSave;
    }

    private int getLastFreeId() {
        return TravelAgencyMapper.toId.applyAsInt(travelAgencyRepository.getAll().getLast()) + 1;
    }
}
