package com.app.persistence.deserializer.impl;

import com.app.model.Countries;
import com.app.persistence.converter.JsonConverter;
import com.app.persistence.deserializer.JsonDeserializer;
import com.app.persistence.deserializer.generic.AbstractJsonDeserializer;

public class CountriesDeserializer extends AbstractJsonDeserializer<Countries> implements JsonDeserializer<Countries> {
    public CountriesDeserializer(JsonConverter<Countries> converter) {
        super(converter);
    }
}
