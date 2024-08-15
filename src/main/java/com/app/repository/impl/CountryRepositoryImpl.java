package com.app.repository.impl;

import com.app.model.country.Country;
import com.app.repository.CountryRepository;
import com.app.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;

import java.util.Optional;

public class CountryRepositoryImpl extends AbstractCrudRepository<Country, Integer> implements CountryRepository {
    public CountryRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public Optional<Country> findByCountry(String country) {
        var sql = "SELECT * FROM %s WHERE country = :country".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("country", country)
                .mapToBean(type)
                .findFirst());
    }
}
