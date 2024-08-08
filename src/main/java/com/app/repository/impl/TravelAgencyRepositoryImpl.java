package com.app.repository.impl;

import com.app.model.agency.TravelAgency;
import com.app.repository.TravelAgencyRepository;
import com.app.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;

import java.util.List;

public class TravelAgencyRepositoryImpl extends AbstractCrudRepository<TravelAgency, Integer>
        implements TravelAgencyRepository {
    public TravelAgencyRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public List<TravelAgency> getByCity(String city) {
        var sql = "SELECT * FROM %s WHERE city = :city".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                        .createQuery(sql)
                        .bind("city", city)
                        .mapToBean(type)
                .list());
    }
}
