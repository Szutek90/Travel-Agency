package com.app.repository.impl;

import com.app.converter.countries.FileToCountriesConverter;
import com.app.model.country.Country;
import com.app.repository.CountryRepository;
import com.app.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Repository
public class CountryRepositoryImpl extends AbstractCrudRepository<Country, Integer> implements CountryRepository {
    private final ApplicationContext context;

    @Value("${countries.file}")
    String filename;

    @Value("${countries.format}")
    String format;

    public CountryRepositoryImpl(Jdbi jdbi, ApplicationContext context) {
        super(jdbi);
        this.context = context;
    }

    @PostConstruct
    public void init() {
        var converter = context.getBean("%sFileToCountriesConverter".formatted(format)
                , FileToCountriesConverter.class);
        if (findAll().isEmpty()) {
            saveAll(converter.convert(filename));
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
