package com.app.persistence.converter.impl;

import com.app.model.country.Countries;
import com.app.model.country.Country;
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
        var expected = new Countries(List.of(new Country(1, "Poland"),
                new Country(2, "Australia")));
        Assertions.assertThat(converter.fromJson(fileReader, Countries.class)).isEqualTo(expected);
    }
}
