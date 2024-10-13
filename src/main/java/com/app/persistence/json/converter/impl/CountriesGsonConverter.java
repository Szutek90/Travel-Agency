package com.app.persistence.json.converter.impl;

import com.app.persistence.model.country.CountriesData;
import com.app.persistence.json.converter.JsonConverter;
import com.app.persistence.json.converter.generic.AbstractGsonConverter;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class CountriesGsonConverter extends AbstractGsonConverter<CountriesData> implements JsonConverter<CountriesData> {
    public CountriesGsonConverter(Gson gson) {
        super(gson);
    }
}
