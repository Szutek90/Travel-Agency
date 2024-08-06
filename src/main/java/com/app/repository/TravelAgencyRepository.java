package com.app.repository;

import com.app.model.agency.TravelAgency;
import com.app.repository.generic.CrudRepository;

import java.util.Optional;

public interface TravelAgencyRepository extends CrudRepository<TravelAgency, Integer> {
    Optional<TravelAgency> getByCity(String city);
}
