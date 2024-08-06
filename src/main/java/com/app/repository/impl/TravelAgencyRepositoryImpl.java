package com.app.repository.impl;

import com.app.model.agency.TravelAgency;
import com.app.repository.TravelAgencyRepository;
import com.app.repository.generic.AbstractCrudRepository;

import java.util.Optional;

public class TravelAgencyRepositoryImpl extends AbstractCrudRepository<TravelAgency, Integer>
        implements TravelAgencyRepository {
    @Override
    public Optional<TravelAgency> getByCity(String city) {
        return Optional.empty();
    }
}
