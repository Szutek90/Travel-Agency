package com.app.repository;

import com.app.model.country.Countries;
import com.app.model.country.Country;
import com.app.repository.generic.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CountryRepository extends CrudRepository<Country, Integer> {
    List<Country> findByCountry(String country);
}
