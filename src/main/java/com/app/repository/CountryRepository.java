package com.app.repository;

import com.app.model.country.Countries;
import com.app.model.country.Country;
import com.app.repository.generic.CrudRepository;

import java.util.Optional;

public interface CountryRepository extends CrudRepository<Countries, Integer> {
    Optional<Country> findByCountry(String country);
}
