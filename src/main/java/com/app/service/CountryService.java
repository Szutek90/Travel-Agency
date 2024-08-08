package com.app.service;

import com.app.dto.CountryDto;
import com.app.model.country.Country;
import com.app.repository.CountryRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CountryService {
    private final CountryRepository countryRepository;

    public Country add(CountryDto countryDto) {
        if (!countryRepository.findByCountry(countryDto.name()).isEmpty()) {
            throw new IllegalArgumentException("Country already exists");
        }

        return countryRepository.save(Country.builder()
                .name(countryDto.name())
                .build());
    }
}
