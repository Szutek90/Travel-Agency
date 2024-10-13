package com.app.persistence.json.converter.impl;

import com.app.model.tour.Tours;
import com.app.persistence.json.converter.JsonConverter;
import com.app.persistence.json.converter.generic.AbstractGsonConverter;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class ToursGsonConverter extends AbstractGsonConverter<Tours> implements JsonConverter<Tours> {
    public ToursGsonConverter(Gson gson) {
        super(gson);
    }
}
