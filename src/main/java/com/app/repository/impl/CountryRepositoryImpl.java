package com.app.repository.impl;

import com.app.model.country.Country;
import com.app.persistence.json.deserializer.JsonDeserializer;
import com.app.persistence.model.country.CountriesData;
import com.app.repository.CountryRepository;
import com.app.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Repository
public class CountryRepositoryImpl extends AbstractCrudRepository<Country, Integer> implements CountryRepository {
    private final JsonDeserializer<CountriesData> deserializer;

    @Value("${countries.file}")
    private String filename;

    public CountryRepositoryImpl(Jdbi jdbi, JsonDeserializer<CountriesData> deserializer) {
        super(jdbi);
        this.deserializer = deserializer;
    }

    @PostConstruct
    public void init() {
        if (findAll().isEmpty()) {
            saveAll(deserializer.deserialize(filename).getConvertedToCountries());
        }
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
