package com.app.service;

import com.app.dto.country.CreateCountryDto;
import com.app.dto.country.GetCountryDto;

import java.util.List;

public interface CountryService {
    GetCountryDto addCountry(CreateCountryDto countryDto);
    List<GetCountryDto> getAllCountries();
}
