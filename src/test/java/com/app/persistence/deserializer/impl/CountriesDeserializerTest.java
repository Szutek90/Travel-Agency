package com.app.persistence.deserializer.impl;

import com.app.model.country.Countries;
import com.app.model.country.Country;
import com.app.persistence.converter.impl.CountriesGsonConverter;
import com.google.gson.GsonBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class CountriesDeserializerTest {

    @Test
    @DisplayName("When deserializing")
    void test1(){
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var converter = new CountriesGsonConverter(gson);
        var expected = new Countries(List.of(new Country(1, "Poland"),
                new Country(2, "Australia")));
        var deserializer = new CountriesDeserializer(converter);

        Assertions.assertThat(deserializer.deserialize("src/test/resources/countriesTest.json"))
                .isEqualTo(expected);
    }
}
