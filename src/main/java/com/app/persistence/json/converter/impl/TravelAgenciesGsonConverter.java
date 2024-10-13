package com.app.persistence.json.converter.impl;

import com.app.model.agency.TravelAgencies;
import com.app.persistence.json.converter.JsonConverter;
import com.app.persistence.json.converter.generic.AbstractGsonConverter;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class TravelAgenciesGsonConverter extends AbstractGsonConverter<TravelAgencies> implements JsonConverter<TravelAgencies> {
    public TravelAgenciesGsonConverter(Gson gson) {
        super(gson);
    }
}
