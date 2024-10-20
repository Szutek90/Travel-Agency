package com.app.persistence.json.deserializer.impl;

import com.app.persistence.json.converter.JsonConverter;
import com.app.persistence.json.deserializer.JsonDeserializer;
import com.app.persistence.json.deserializer.generic.AbstractJsonDeserializer;
import com.app.persistence.model.agency.TravelAgenciesData;
import org.springframework.stereotype.Component;

@Component
public class TravelAgenciesJsonDeserializer extends AbstractJsonDeserializer<TravelAgenciesData>
        implements JsonDeserializer<TravelAgenciesData> {

    public TravelAgenciesJsonDeserializer(JsonConverter<TravelAgenciesData> converter) {
        super(converter);
    }
}
