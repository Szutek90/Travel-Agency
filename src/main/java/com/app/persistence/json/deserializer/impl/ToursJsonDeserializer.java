package com.app.persistence.json.deserializer.impl;

import com.app.persistence.model.tour.ToursData;
import com.app.persistence.json.converter.JsonConverter;
import com.app.persistence.json.deserializer.JsonDeserializer;
import com.app.persistence.json.deserializer.generic.AbstractJsonDeserializer;

public class ToursJsonDeserializer extends AbstractJsonDeserializer<ToursData> implements JsonDeserializer<ToursData> {
    public ToursJsonDeserializer(JsonConverter<ToursData> converter) {
        super(converter);
    }
}
