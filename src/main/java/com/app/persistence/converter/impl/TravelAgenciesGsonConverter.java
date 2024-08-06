package com.app.persistence.converter.impl;

import com.app.model.agency.TravelAgencies;
import com.app.persistence.converter.JsonConverter;
import com.app.persistence.converter.generic.AbstractGsonConverter;
import com.google.gson.Gson;

public class TravelAgenciesGsonConverter extends AbstractGsonConverter<TravelAgencies> implements JsonConverter<TravelAgencies> {
    public TravelAgenciesGsonConverter(Gson gson) {
        super(gson);
    }
}
