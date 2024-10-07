package com.app.repository.impl;

import com.app.model.agency.TravelAgency;
import com.app.repository.TravelAgencyRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class TravelAgencyRepositoryImplTest {
    static TravelAgencyRepository travelAgencyRepository;

    @BeforeAll
    static void beforeAll() {
        travelAgencyRepository = new TravelAgencyRepositoryImpl(new ArrayList<>(Arrays.asList(
                new TravelAgency(1, "Agencja", "Warszawa", "123456789"),
                new TravelAgency(2, "WroclawAgency", "Wroclaw", "987654321")
        )));
    }

    @Test
    @DisplayName("When finding by id")
    void test1() {
        var expected = new TravelAgency(1, "Agencja", "Warszawa", "123456789");
        assertThat(travelAgencyRepository.findById(1)
                .orElseThrow(() -> new IllegalStateException("No agency with given id")))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("When finding by agency name")
    void test2() {
        var expected = new TravelAgency(2, "WroclawAgency", "Wroclaw", "987654321");
        assertThat(travelAgencyRepository.findByName("WroclawAgency")
                .orElseThrow(() -> new IllegalStateException("No agency with given name")))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("When finding by city")
    void test3() {
        var expected = new TravelAgency(2, "WroclawAgency", "Wroclaw", "987654321");
        assertThat(travelAgencyRepository.findByCity("Wroclaw"))
                .isEqualTo(List.of(expected));
    }

    @Test
    @DisplayName("When getting all")
    void test4() {
        var expected = List
                .of(new TravelAgency(1, "Agencja", "Warszawa", "123456789"),
                        new TravelAgency(2, "WroclawAgency", "Wroclaw", "987654321"));
        assertThat(travelAgencyRepository.getAll()).isEqualTo(expected);
    }

    @Test
    @DisplayName("When saving")
    void test5() {
        var expected = List
                .of(new TravelAgency(1, "Agencja", "Warszawa", "123456789"),
                        new TravelAgency(2, "WroclawAgency", "Wroclaw", "987654321"),
                        new TravelAgency(3, "X", "Bialystok", "321"));
        var agencyToSave = new TravelAgency(3, "X", "Bialystok", "321");
        System.out.println(travelAgencyRepository.getAll());
        assertThatCode(() -> travelAgencyRepository.save(agencyToSave))
                .doesNotThrowAnyException();
        assertThat(travelAgencyRepository.getAll()).isEqualTo(expected);
    }

    @Test
    @DisplayName("When deleting")
    void test6() {
        var expected = List
                .of(new TravelAgency(3, "X", "Bialystok", "321"));
        assertThatCode(() -> travelAgencyRepository.delete(1)).doesNotThrowAnyException();
        assertThatCode(() -> travelAgencyRepository.delete(2)).doesNotThrowAnyException();
        assertThat(travelAgencyRepository.getAll()).isEqualTo(expected);
    }

    @Test
    @DisplayName("When saving all")
    void test7() {
        var expected = List
                .of(new TravelAgency(1, "Agencja", "Warszawa", "123456789"),
                        new TravelAgency(2, "WroclawAgency", "Wroclaw", "987654321"),
                        new TravelAgency(3, "X", "Bialystok", "321"));
        var agenciesToSave = List
                .of(new TravelAgency(1, "Agencja", "Warszawa", "123456789"),
                        new TravelAgency(2, "WroclawAgency", "Wroclaw", "987654321"));
        assertThatCode(() -> travelAgencyRepository.saveAll(agenciesToSave))
                .doesNotThrowAnyException();
        assertThat(travelAgencyRepository.getAll()).containsExactlyInAnyOrderElementsOf(expected);
    }
}
