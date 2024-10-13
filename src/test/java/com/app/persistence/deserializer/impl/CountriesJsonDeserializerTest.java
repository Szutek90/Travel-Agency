package com.app.persistence.deserializer.impl;

import com.app.persistence.model.country.CountriesData;
import com.app.persistence.model.country.CountryData;
import com.app.persistence.json.converter.impl.CountriesGsonConverter;
import com.app.persistence.json.deserializer.impl.CountriesJsonDeserializer;
import com.google.gson.GsonBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class CountriesJsonDeserializerTest {

    @Test
    @DisplayName("When deserializing")
    void test1(){
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var converter = new CountriesGsonConverter(gson);
        var expected = new CountriesData(List.of(new CountryData(1, "Poland"),
                new CountryData(2, "Australia")));
        var deserializer = new CountriesJsonDeserializer(converter);

        Assertions.assertThat(deserializer.deserialize("src/test/resources/countriesTest.json")
                        .getCountries()).isEqualTo(expected.getCountries());
    }
}
