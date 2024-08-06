package com.app.persistence.deserializer.impl;

import com.app.model.agency.TravelAgencies;
import com.app.persistence.converter.JsonConverter;
import com.app.persistence.deserializer.JsonDeserializer;
import com.app.persistence.deserializer.generic.AbstractJsonDeserializer;

public class TravelAgenciesDeserializer extends AbstractJsonDeserializer<TravelAgencies>
        implements JsonDeserializer<TravelAgencies> {

    public TravelAgenciesDeserializer(JsonConverter<TravelAgencies> converter) {
        super(converter);
    }
}
