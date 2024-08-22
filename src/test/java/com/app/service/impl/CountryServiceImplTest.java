package com.app.service.impl;


import com.app.dto.CountryDto;
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

import java.util.Optional;

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
        var countryDto = new CountryDto("Majorka");
        Mockito.when(repository.findByCountry(Mockito.anyString()))
                .thenReturn(Optional.empty());
        Mockito.when(repository.save(Mockito.any(Country.class)))
                .thenReturn(new Country(2, "Polska"));
        Assertions.assertThatCode(() -> service.addCountry(countryDto)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("When country already exists")
    void test2() {
        var countryDto = new CountryDto("Majorka");
        Mockito.when(repository.findByCountry(Mockito.anyString()))
                .thenReturn(Optional.of(new Country(1, "Polska")));
        Assertions.assertThatThrownBy(() -> service.addCountry(countryDto))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
