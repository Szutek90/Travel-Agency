package com.app.service.impl;


import com.app.dto.country.CreateCountryDto;
import com.app.dto.country.GetCountryDto;
import com.app.model.country.Country;
import com.app.repository.CountryRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CountryServiceImplTest {
    @Mock
    private CountryRepository repository;

    @InjectMocks
    private CountryServiceImpl service;

    @Test
    @DisplayName("When adding country")
    void test1() {
        var countryDto = new CreateCountryDto("Majorka");
        when(repository.findByCountry(anyString()))
                .thenReturn(Optional.empty());
        when(repository.save(any(Country.class)))
                .thenReturn(new Country(2, "Polska"));
        assertThatCode(() -> service.addCountry(countryDto)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("When country already exists")
    void test2() {
        var countryDto = new CreateCountryDto("Majorka");
        when(repository.findByCountry(anyString()))
                .thenReturn(Optional.of(new Country(1, "Polska")));
        assertThatThrownBy(() -> service.addCountry(countryDto))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("When getting al countries")
    void test3() {
        var countries = List.of(
            new Country(1, "Majorka"),
            new Country(2,"Norway")
        );
        when(repository.findAll()).thenReturn(countries);
        assertThat(service.getAllCountries()).isEqualTo(countries
                .stream()
                .map(Country::toGetCountryDto)
                .toList());
    }
}
