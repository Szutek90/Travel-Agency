package com.app.repository.impl;

import com.app.model.country.Country;
import com.app.repository.CountryRepository;
import com.app.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CountryRepositoryImpl extends AbstractCrudRepository<Country, Integer> implements CountryRepository {
    public CountryRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public Optional<Country> findByCountry(String countryName) {
        var sql = "SELECT * FROM %s WHERE name = :countryName".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("countryName", countryName)
                .mapToBean(type)
                .findFirst());
    }
}
