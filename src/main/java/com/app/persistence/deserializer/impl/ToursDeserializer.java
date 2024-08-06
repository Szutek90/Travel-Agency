package com.app.persistence.deserializer.impl;

import com.app.model.tour.Tours;
import com.app.persistence.converter.JsonConverter;
import com.app.persistence.deserializer.JsonDeserializer;
import com.app.persistence.deserializer.generic.AbstractJsonDeserializer;

public class ToursDeserializer extends AbstractJsonDeserializer<Tours> implements JsonDeserializer<Tours> {
    public ToursDeserializer(JsonConverter<Tours> converter) {
        super(converter);
    }
}
