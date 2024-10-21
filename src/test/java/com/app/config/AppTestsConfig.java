package com.app.config;

import com.app.converter.countries.FileToCountriesConverter;
import com.app.converter.countries.impl.JsonFileToCountriesConverterImpl;
import com.app.converter.tours.FileToToursConverter;
import com.app.converter.tours.impl.JsonFileToToursConverterImpl;
import com.app.persistence.json.converter.JsonConverter;
import com.app.persistence.json.converter.impl.CountriesGsonConverter;
import com.app.persistence.json.converter.impl.ToursGsonConverter;
import com.app.persistence.json.deserializer.JsonDeserializer;
import com.app.persistence.json.deserializer.custom.LocalDateDeserializer;
import com.app.persistence.json.deserializer.custom.LocalDateSerializer;
import com.app.persistence.json.deserializer.impl.CountriesJsonDeserializer;
import com.app.persistence.json.deserializer.impl.ToursJsonDeserializer;
import com.app.persistence.model.country.CountriesData;
import com.app.persistence.model.tour.ToursData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

public class AppTestsConfig {
    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .setPrettyPrinting().create();
    }

    @Bean
    public JsonConverter<CountriesData> countriesJsonConverter() {
        return new CountriesGsonConverter(gson());
    }

    @Bean
    public JsonDeserializer<CountriesData> countriesDataJsonDeserializer() {
        return new CountriesJsonDeserializer(countriesJsonConverter());
    }

    @Bean
    public JsonConverter<ToursData> toursDataJsonConverter() {
        return new ToursGsonConverter(gson());
    }

    @Bean
    public JsonDeserializer<ToursData> toursDeserializer() {
        return new ToursJsonDeserializer(toursDataJsonConverter());
    }

    @Bean
    public FileToToursConverter fileToToursConverter() {
        return new JsonFileToToursConverterImpl(toursDeserializer());
    }

    @Bean
    public FileToCountriesConverter fileToCountriesConverter(){
        return new JsonFileToCountriesConverterImpl(countriesDataJsonDeserializer());
    }

}
