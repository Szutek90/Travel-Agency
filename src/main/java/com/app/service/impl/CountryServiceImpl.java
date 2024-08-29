package com.app.service.impl;

import com.app.dto.country.CreateCountryDto;
import com.app.dto.country.GetCountryDto;
import com.app.model.country.Country;
import com.app.repository.CountryRepository;
import com.app.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;

    public GetCountryDto addCountry(CreateCountryDto countryDto) {
        if (countryRepository.findByCountry(countryDto.name()).isPresent()) {
            throw new IllegalArgumentException("Country already exists");
        }

        var addedCountry = countryRepository.save(Country.builder()
                .name(countryDto.name())
                .build());
        return addedCountry.toGetCountryDto();
    }

    public List<GetCountryDto> getAllCountries() {
        return countryRepository.findAll()
                .stream()
                .map(Country::toGetCountryDto)
                .toList();
    }
}
