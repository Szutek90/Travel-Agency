package com.app.persistence.json.deserializer.impl;

import com.app.model.agency.TravelAgencies;
import com.app.persistence.json.converter.JsonConverter;
import com.app.persistence.json.deserializer.JsonDeserializer;
import com.app.persistence.json.deserializer.generic.AbstractJsonDeserializer;

public class TravelAgenciesJsonDeserializer extends AbstractJsonDeserializer<TravelAgencies>
        implements JsonDeserializer<TravelAgencies> {

    public TravelAgenciesJsonDeserializer(JsonConverter<TravelAgencies> converter) {
        super(converter);
    }
}
