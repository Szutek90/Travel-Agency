package com.app.persistence.deserializer.impl;

import com.app.model.country.Countries;
import com.app.model.country.Country;
import com.app.model.tour.Tour;
import com.app.model.tour.Tours;
import com.app.persistence.converter.impl.CountriesGsonConverter;
import com.app.persistence.converter.impl.ToursGsonConverter;
import com.app.persistence.deserializer.custom.LocalDateDeserializer;
import com.google.gson.GsonBuilder;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class ToursDeserializerTest {

    @Test
    @DisplayName("When deserializing")
    void test1(){
        var gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .setPrettyPrinting().create();
        var converter = new ToursGsonConverter(gson);
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
        var deserializer = new ToursDeserializer(converter);

        Assertions.assertThat(deserializer.deserialize("src/test/resources/toursTest.json"))
                .isEqualTo(expected);
    }
}
