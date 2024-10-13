package com.app.persistence.json.converter.impl;

import com.app.persistence.json.converter.JsonConverter;
import com.app.persistence.json.converter.generic.AbstractGsonConverter;
import com.app.persistence.model.agency.TravelAgenciesData;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class TravelAgenciesGsonConverter extends AbstractGsonConverter<TravelAgenciesData> implements JsonConverter<TravelAgenciesData> {
    public TravelAgenciesGsonConverter(Gson gson) {
        super(gson);
    }
}
