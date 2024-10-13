package com.app.persistence.json.converter.impl;

import com.app.persistence.model.tour.ToursData;
import com.app.persistence.json.converter.JsonConverter;
import com.app.persistence.json.converter.generic.AbstractGsonConverter;
import com.google.gson.Gson;
import org.springframework.stereotype.Component;

@Component
public class ToursGsonConverter extends AbstractGsonConverter<ToursData> implements JsonConverter<ToursData> {
    public ToursGsonConverter(Gson gson) {
        super(gson);
    }
}
