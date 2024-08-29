package com.app.service.impl;

import com.app.dto.reservation.CreateReservationDto;
import com.app.model.ReservationComponent;
import com.app.model.TourWithClosestAvgPriceByAgency;
import com.app.model.agency.TravelAgency;
import com.app.model.country.Country;
import com.app.model.person.Person;
import com.app.model.reservation.Reservation;
import com.app.model.tour.Tour;
import com.app.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class ReservationServiceImplTest {
    static CreateReservationDto createReservationDto;
    static Reservation reservation;
    static TravelAgency agency;
    static Tour tour;

    @Mock
    ReservationRepository reservationRepository;
    @Mock
    TourRepository tourRepository;
    @Mock
    PersonRepository personRepository;
    @Mock
    TravelAgencyRepository travelAgencyRepository;
    @Mock
    CountryRepository countryRepository;

    @InjectMocks
    ReservationServiceImpl service;

    @BeforeAll
    static void beforeAll() {
        var person = Person.builder().name("jan").surname("kowalski").id(1)
                .email("jkowal@wp.pl").build();
        createReservationDto = new CreateReservationDto(person, new Tour(1, 1, 1, BigDecimal.ONE, LocalDate.now(),
                LocalDate.now()),
                new TravelAgency(1, "Agency", "Warszawa", "123456"),
                10, 0, List.of(ReservationComponent.ALL_INCLUSIVE));
        reservation = new Reservation(1, 1, 1, 1, 10, 0);
        agency = new TravelAgency(1, "Agencja 1", "Warszawa", "123456");
        tour = new Tour(1, 1, 1, BigDecimal.TEN, LocalDate.now(),
                LocalDate.now());
    }

    @Test
    @DisplayName("When making reservation")
    void tes1() {
        when(tourRepository.findById(anyInt())).thenReturn(Optional.of(new Tour()));
        when(personRepository.findById(anyInt())).thenReturn(Optional.of(Person.builder()
                .id(1)
                .email("kowalski@wp.pl")
                .name("Jan")
                .surname("kowalski")
                .build()));
        when(reservationRepository.save(any(Reservation.class))).thenReturn(reservation);
        assertThatCode(() -> service.makeReservation(createReservationDto))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("When tour is not found while making reservation")
    void test2() {
        assertThatThrownBy(() -> service.makeReservation(createReservationDto))
                .isInstanceOf(IllegalStateException.class).hasMessage("tour not found");
    }

    @Test
    @DisplayName("When person is not found while making reservation")
    void test3() {
        when(tourRepository.findById(anyInt())).thenReturn(Optional.of(new Tour()));
        assertThatThrownBy(() -> service.makeReservation(createReservationDto))
                .isInstanceOf(IllegalStateException.class).hasMessage("person not found");
    }

    @Test
    @DisplayName("When deleting reservation")
    void test4() {
        when(reservationRepository.deleteById(anyInt())).thenReturn(reservation);
        assertThatCode(() -> service.deleteReservation(8)).doesNotThrowAnyException();
    }

    @Test
    @DisplayName("When getting agency with most organized trips")
    void test5() {
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));
        when(travelAgencyRepository.findById(anyInt())).thenReturn(Optional.of(agency));
        assertThat(service.getAgencyWithMostOrganizedTrips()).containsExactly(agency).hasSize(1);
    }

    @Test
    @DisplayName("When getting agencies most earned money")
    void test6() {
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));
        when(tourRepository.findById(anyInt())).thenReturn(Optional.of(tour));
        when(travelAgencyRepository.findById(anyInt())).thenReturn(Optional.of(agency));
        assertThat(service.getAgencyEarnMostMoney()).containsExactly(agency).hasSize(1);
    }

    @Test
    @DisplayName("When getting most visited countries")
    void test7() {
        var country = new Country(1, "Iceland");
        when(reservationRepository.findAll()).thenReturn(List.of(reservation));
        when(countryRepository.findById(anyInt()))
                .thenReturn(Optional.of(country));
        when(tourRepository.findById(anyInt())).thenReturn(Optional.of(tour));
        assertThat(service.getMostVisitedCountries()).containsExactly(country).hasSize(1);
    }

    @Test
    @DisplayName("When getting summary")
    void test8() {
        var tourWithAvgPrice = new TourWithClosestAvgPriceByAgency(BigDecimal.TEN, tour);
        var expected = new HashMap<TravelAgency, TourWithClosestAvgPriceByAgency>();
        expected.put(agency, tourWithAvgPrice);
        when(tourRepository.findAll()).thenReturn(List.of(tour));
        when(travelAgencyRepository.findById(anyInt())).thenReturn(Optional.of(agency));
        assertThat(service.getSummaryByTourAvgPrice())
                .containsExactlyEntriesOf(expected);
    }

    @Test
    @DisplayName("When getting tours in given list of countries")
    void test9() {
        when(tourRepository.getByCountryName("Poland")).thenReturn(List.of(tour));
        assertThat(service.getToursTakingPlaceInGivenCountry(List.of("Poland", "Norway")))
                .isEqualTo(List.of(tour));
    }
}
