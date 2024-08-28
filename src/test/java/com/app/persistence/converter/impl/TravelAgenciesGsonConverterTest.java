package com.app.persistence.converter.impl;

import com.app.model.agency.TravelAgencies;
import com.app.model.agency.TravelAgency;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

class TravelAgenciesGsonConverterTest {
    static Gson gson;
    static TravelAgenciesGsonConverter converter;
    static FileReader fileReader;

    @BeforeAll
    static void beforeAll() throws FileNotFoundException {
        gson = new GsonBuilder().setPrettyPrinting().create();
        converter = new TravelAgenciesGsonConverter(gson);
        fileReader = new FileReader("src/test/resources/agenciesTest.json");
    }

    @Test
    @DisplayName("When converting from json")
    void test1(){
        var expected = new TravelAgencies(List.of(
                new TravelAgency(1,"Sunshine Travels", "Warszawa", "+48 123 456 789"),
                new TravelAgency(2,"Adventure Seekers", "Kraków", "+48 987 654 321")));
        Assertions.assertThat(converter.fromJson(fileReader, TravelAgencies.class)).isEqualTo(expected);
    }
}
