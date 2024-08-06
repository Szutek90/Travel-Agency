package com.app.persistence.converter.impl;

import com.app.model.Countries;
import com.app.persistence.converter.JsonConverter;
import com.app.persistence.converter.generic.AbstractGsonConverter;
import com.google.gson.Gson;

public class CountriesGsonConverter extends AbstractGsonConverter<Countries> implements JsonConverter<Countries> {
    public CountriesGsonConverter(Gson gson) {
        super(gson);
    }
}
