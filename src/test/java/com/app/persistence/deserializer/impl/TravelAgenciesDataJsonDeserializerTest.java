package com.app.persistence.deserializer.impl;

import com.app.model.agency.TravelAgencies;
import com.app.model.agency.TravelAgency;
import com.app.persistence.json.converter.impl.TravelAgenciesGsonConverter;
import com.app.persistence.json.deserializer.impl.TravelAgenciesJsonDeserializer;
import com.google.gson.GsonBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

class TravelAgenciesDataJsonDeserializerTest {
    @Test
    @DisplayName("When deserializing")
    void test1(){
        var gson = new GsonBuilder().setPrettyPrinting().create();
        var converter = new TravelAgenciesGsonConverter(gson);
        var expected = new TravelAgencies(List.of(
                new TravelAgency(1,"Sunshine Travels", "Warszawa", "+48 123 456 789"),
                new TravelAgency(2,"Adventure Seekers", "Kraków", "+48 987 654 321")));
        var deserializer = new TravelAgenciesJsonDeserializer(converter);

        Assertions.assertThat(deserializer.deserialize("src/test/resources/agenciesTest.json"))
                .isEqualTo(expected);
    }
}

