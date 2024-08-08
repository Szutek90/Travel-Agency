package com.app.repository.impl;

import com.app.model.agency.TravelAgency;
import com.app.repository.TravelAgencyRepository;
import com.app.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;

import java.util.Optional;

public class TravelAgencyRepositoryImpl extends AbstractCrudRepository<TravelAgency, Integer>
        implements TravelAgencyRepository {
    public TravelAgencyRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public Optional<TravelAgency> getByCity(String city) {
        return Optional.empty();
    }
}
