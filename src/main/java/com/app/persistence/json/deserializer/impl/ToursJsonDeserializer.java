package com.app.persistence.json.deserializer.impl;

import com.app.model.tour.Tours;
import com.app.persistence.json.converter.JsonConverter;
import com.app.persistence.json.deserializer.JsonDeserializer;
import com.app.persistence.json.deserializer.generic.AbstractJsonDeserializer;

public class ToursJsonDeserializer extends AbstractJsonDeserializer<Tours> implements JsonDeserializer<Tours> {
    public ToursJsonDeserializer(JsonConverter<Tours> converter) {
        super(converter);
    }
}
