package com.app.repository;

import com.app.model.agency.TravelAgency;

import java.util.List;
import java.util.Optional;

public interface TravelAgencyRepository {
    Optional<TravelAgency> findById(int id);

    Optional<TravelAgency> findByName(String name);

    List<TravelAgency> findByCity(String city);

    List<TravelAgency> getAll();

    int save(TravelAgency travelAgency);

    List<TravelAgency> saveAll(List<TravelAgency> travelAgencies);

    TravelAgency delete(int id);
}
