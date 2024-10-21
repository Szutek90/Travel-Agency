package com.app.repository.impl;

import com.app.model.agency.TravelAgency;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class TravelAgencyRepositoryImplTest {
    static TravelAgencyRepositoryImpl repository = new TravelAgencyRepositoryImpl(null);

    @BeforeAll
    static void beforeAll() {
        repository.saveAll(new ArrayList<>(Arrays.asList(
                new TravelAgency(1, "Agencja", "Warszawa", "123456789"),
                new TravelAgency(2, "WroclawAgency", "Wroclaw", "987654321")
        )));
    }

    @Test
    @DisplayName("When finding by id")
    void test1() {
        var expected = new TravelAgency(1, "Agencja", "Warszawa", "123456789");
        assertThat(repository.findById(1)
                .orElseThrow(() -> new IllegalStateException("No agency with given id")))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("When finding by agency name")
    void test2() {
        var expected = new TravelAgency(2, "WroclawAgency", "Wroclaw", "987654321");
        assertThat(repository.findByName("WroclawAgency")
                .orElseThrow(() -> new IllegalStateException("No agency with given name")))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("When finding by city")
    void test3() {
        var expected = new TravelAgency(2, "WroclawAgency", "Wroclaw", "987654321");
        assertThat(repository.findByCity("Wroclaw"))
                .isEqualTo(List.of(expected));
    }

    @Test
    @DisplayName("When getting all")
    void test4() {
        var expected = List
                .of(new TravelAgency(1, "Agencja", "Warszawa", "123456789"),
                        new TravelAgency(2, "WroclawAgency", "Wroclaw", "987654321"));
        assertThat(repository.getAll()).isEqualTo(expected);
    }

    @Test
    @DisplayName("When saving")
    void test5() {
        var expected = List
                .of(new TravelAgency(1, "Agencja", "Warszawa", "123456789"),
                        new TravelAgency(2, "WroclawAgency", "Wroclaw", "987654321"),
                        new TravelAgency(3, "X", "Bialystok", "321"));
        var agencyToSave = new TravelAgency(3, "X", "Bialystok", "321");
        System.out.println(repository.getAll());
        assertThatCode(() -> repository.save(agencyToSave))
                .doesNotThrowAnyException();
        assertThat(repository.getAll()).isEqualTo(expected);
    }

    @Test
    @DisplayName("When deleting")
    void test6() {
        var expected = List
                .of(new TravelAgency(3, "X", "Bialystok", "321"));
        assertThatCode(() -> repository.delete(1)).doesNotThrowAnyException();
        assertThatCode(() -> repository.delete(2)).doesNotThrowAnyException();
        assertThat(repository.getAll()).isEqualTo(expected);
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
        assertThatCode(() -> repository.saveAll(agenciesToSave))
                .doesNotThrowAnyException();
        assertThat(repository.getAll()).containsExactlyInAnyOrderElementsOf(expected);
    }
}
