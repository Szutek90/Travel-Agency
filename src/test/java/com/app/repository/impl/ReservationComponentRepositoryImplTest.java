package com.app.repository.impl;

import com.app.extension.DbTablesEachExtension;
import com.app.model.ReservationComponent;
import com.app.model.agency.TravelAgency;
import com.app.model.country.Country;
import com.app.model.person.Person;
import com.app.model.reservation.Reservation;
import com.app.model.tour.Tour;
import com.app.repository.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(DbTablesEachExtension.class)
@Testcontainers(disabledWithoutDocker = true)
class ReservationComponentRepositoryImplTest extends Base {
    static ReservationComponentRepository componentRepository;
    static ReservationRepository reservationRepository;
    static TourRepository tourRepository;
    static TravelAgencyRepository travelAgencyRepository;
    static CountryRepository countryRepository;
    static PersonRepository personRepository;

    @BeforeAll
    static void beforeAll() {
        var jdbi = jdbiExtension.getJdbi();
        componentRepository = new ReservationComponentRepositoryImpl(jdbi);
        reservationRepository = new ReservationRepositoryImpl(jdbi);
        tourRepository = new TourRepositoryImpl(jdbi);
        travelAgencyRepository = new TravelAgencyRepositoryImpl(List
                .of(new TravelAgency(1, "Agencja", "Warszawa", "123456789")));
        countryRepository = new CountryRepositoryImpl(jdbi);
        personRepository = new PersonRepositoryImpl(jdbi);
        DbTablesEachExtension.setJdbi(jdbi);
    }

    @BeforeEach
    void beforeEach() {
        var tour = new Tour(1, 1, 1, BigDecimal.TEN,
                LocalDate.of(2024, 10, 19), LocalDate.now());
        var reservation = new Reservation(1, 1, 1, 1, 2, 0);
        var country = new Country(1, "Poland");
        var customer = new Person(1, "Jan", "Kowalski", "kowal@wp.pl");
        personRepository.save(customer);
        countryRepository.save(country);
        tourRepository.save(tour);
        reservationRepository.save(reservation);

    }

    @Test
    @DisplayName("When saving reservation component")
    void test1() {
        var componentToSave = ReservationComponent.ALL_INCLUSIVE;
        assertThatCode(() -> componentRepository.save(1, componentToSave))
                .doesNotThrowAnyException();
        assertThat(componentRepository.findAll()).isEqualTo(List.of(componentToSave));
    }

    @Test
    @DisplayName("When finding by reservation id")
    void test2() {
        var componentToSave = ReservationComponent.ALL_INCLUSIVE;
        componentRepository.save(1,componentToSave);
        assertThat(componentRepository.findByReservationId(1)).isEqualTo(List.of(componentToSave));
    }
}
