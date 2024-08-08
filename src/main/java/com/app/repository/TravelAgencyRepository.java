package com.app.repository;

import com.app.model.agency.TravelAgency;
import com.app.repository.generic.CrudRepository;

import java.util.List;

public interface TravelAgencyRepository extends CrudRepository<TravelAgency, Integer> {
    List<TravelAgency> getByCity(String city);
}
