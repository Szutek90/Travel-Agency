package com.app.persistence.converter.impl;

import com.app.persistence.model.country.CountriesData;
import com.app.persistence.model.country.CountryData;
import com.app.persistence.json.converter.impl.CountriesGsonConverter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

class CountriesGsonConverterTest {
    static Gson gson;
    static CountriesGsonConverter converter;
    static FileReader fileReader;

    @BeforeAll
    static void beforeAll() throws FileNotFoundException {
        gson = new GsonBuilder().setPrettyPrinting().create();
        converter = new CountriesGsonConverter(gson);
        fileReader = new FileReader("src/test/resources/countriesTest.json");
    }

    @Test
    @DisplayName("When converting from json")
     void test1() {
        var expected = new CountriesData(List.of(new CountryData(1, "Poland"),
                new CountryData(2, "Australia")));
        Assertions.assertThat(converter.fromJson(fileReader, CountriesData.class).getCountries())
                .isEqualTo(expected.getCountries());
    }
}
