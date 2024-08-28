package com.app.persistence.converter.impl;

import com.app.model.country.Countries;
import com.app.model.country.Country;
import com.app.model.tour.Tour;
import com.app.model.tour.Tours;
import com.app.persistence.deserializer.custom.LocalDateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class ToursGsonConverterTest {
    static Gson gson;
    static ToursGsonConverter converter;
    static FileReader fileReader;

    @BeforeAll
    static void beforeAll() throws FileNotFoundException {
        gson = new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
        converter = new ToursGsonConverter(gson);
        fileReader = new FileReader("src/test/resources/toursTest.json");
    }

    @Test
    @DisplayName("When converting from json")
    void test1() {
        var expected = new Tours(List.of(Tour.builder()
                        .id(1)
                        .agencyId(1)
                        .countryId(1)
                        .pricePerPerson(BigDecimal.valueOf(2500))
                        .startDate(LocalDate.of(2024, 9, 1))
                        .endDate(LocalDate.of(2024, 9, 10))
                        .build(),
                Tour.builder()
                        .id(2)
                        .agencyId(2)
                        .countryId(2)
                        .pricePerPerson(BigDecimal.valueOf(3000))
                        .startDate(LocalDate.of(2024, 10, 5))
                        .endDate(LocalDate.of(2024, 10, 15))
                        .build()));
        Assertions.assertThat(converter.fromJson(fileReader, Tours.class)).isEqualTo(expected);
    }
}
