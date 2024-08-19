package com.app.service.impl;

import com.app.dto.CountryDto;
import com.app.model.country.Country;
import com.app.repository.CountryRepository;
import com.app.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public Country addCountry(CountryDto countryDto) {
        if (countryRepository.findByCountry(countryDto.name()).isPresent()) {
            throw new IllegalArgumentException("Country already exists");
        }

        return countryRepository.save(Country.builder()
                .name(countryDto.name())
                .build());
    }
}
