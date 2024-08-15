package com.app.service;

import com.app.dto.CountryDto;
import com.app.model.country.Country;

public interface CountryService {
    Country addCountry(CountryDto countryDto);
}
