package com.app.service.impl;

import com.app.dto.TourDto;
import com.app.model.country.Country;
import com.app.model.tour.Tour;
import com.app.repository.CountryRepository;
import com.app.repository.TourRepository;
import org.assertj.core.api.Assertions;
import org.jdbi.v3.core.collector.ElementTypeNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TourWithCountryServiceImplTest {
    @Mock
    private TourRepository tourRepository;
    @Mock
    private CountryRepository countryRepository;

    @InjectMocks
    private TourWithCountryServiceImpl service;

    @Test
    @DisplayName("When getting by id")
    void test1() {
        when(tourRepository.findById(anyInt()))
                .thenReturn(Optional.of(new Tour(1, 1, 1, BigDecimal.TWO,
                        LocalDate.now(), LocalDate.now())));
        assertThatCode(() -> service.getById(1)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("When getting by id - no element with given id")
    void test2() {
        when(tourRepository.findById(anyInt()))
                .thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.getById(1))
                .isInstanceOf(ElementTypeNotFoundException.class);
    }

    @Test
    @DisplayName("When getting by country name")
    void test3() {
        when(tourRepository.getByCountryName(anyString())).
                thenReturn(Collections.emptyList());
        assertThat(service.getByCountry("Poland")).isEmpty();
    }

    @Test
    @DisplayName("When getting tours in price range")
    void test4() {
        when(tourRepository.getInPriceRange(any(), any())).thenReturn(Collections.emptyList());
        assertThat(service.getToursInPriceRange(BigDecimal.TWO, BigDecimal.TEN)).isEmpty();
    }

    @Test
    @DisplayName("When getting tours cheaper than given range")
    void test5() {
        var expected = Tour.builder()
                .id(1)
                .pricePerPerson(BigDecimal.TEN)
                .agencyId(10)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .countryId(5)
                .build();
        when(tourRepository.getLessThanGiveNPrice(any())).thenReturn(List.of(expected));
        assertThat(service.getToursCheaperThan(BigDecimal.valueOf(50)))
                .containsExactly(expected);
    }

    @Test
    @DisplayName("When getting tours more expensive than than given range")
    void test6() {
        var expected = Tour.builder()
                .id(1)
                .pricePerPerson(BigDecimal.TEN)
                .agencyId(10)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .countryId(5)
                .build();
        when(tourRepository.getMoreExpensiveThanGivenPrice(any())).thenReturn(List.of(expected));
        assertThat(service.getToursMoreExpensiveThan(BigDecimal.valueOf(5)))
                .containsExactly(expected);
    }

    @Test
    @DisplayName("When getting tours in date range")
    void test7() {
        when(tourRepository.getInDateRange(any(), any())).thenReturn(Collections.emptyList());
        assertThat(service.getToursInRange(LocalDate.of(2024, 05, 18), LocalDate.now()))
                .isEmpty();
    }

    @Test
    @DisplayName("When getting tour after given date")
    void test8() {
        var expected = Tour.builder()
                .id(1)
                .pricePerPerson(BigDecimal.TEN)
                .agencyId(10)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .countryId(5)
                .build();
        when(tourRepository.getAfterGivenDate(any())).thenReturn(List.of(expected));
        assertThat(service.getToursAfterDate(LocalDate.of(2024, 05, 18)))
                .isEqualTo(List.of(expected));
    }

    @Test
    @DisplayName("When getting tours before given date")
    void test9() {
        when(tourRepository.getBeforeGivenDate(any())).thenReturn(Collections.emptyList());
        assertThat(service.getToursBeforeDate(LocalDate.of(2024, 05, 18)))
                .isEmpty();
    }

    @Test
    @DisplayName("When creating new Tour")
    void test10() {
        var expected = Tour.builder()
                .id(1)
                .pricePerPerson(BigDecimal.TEN)
                .agencyId(10)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .countryId(1)
                .build();
        when(countryRepository.findByCountry(any())).thenReturn(Optional.of(Country.builder()
                .id(1)
                .name("Finland")
                .build()));
        when(tourRepository.save(any())).thenReturn(expected);
        assertThat(service.createTour(new TourDto("Finland", BigDecimal.ONE,
                LocalDate.of(2014, 5, 18), LocalDate.now())))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("When there is no country while creating new Tour")
    void test11() {
        when(countryRepository.findByCountry(any())).thenReturn(Optional.empty());
        assertThatThrownBy(() -> service.createTour(new TourDto("Finland", BigDecimal.ONE,
                LocalDate.of(2014, 5, 18), LocalDate.now())))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
