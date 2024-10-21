package com.app.converter.agencies.impl;

import com.app.converter.agencies.FileToAgenciesConverter;
import com.app.model.agency.TravelAgency;
import com.app.persistence.json.deserializer.JsonDeserializer;
import com.app.persistence.model.agency.TravelAgenciesData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JsonFileToAgenciesConverterImpl implements FileToAgenciesConverter {
    private final JsonDeserializer<TravelAgenciesData> deserializer;

    @Override
    public List<TravelAgency> convert(String filename) {
        return deserializer.deserialize(filename).getConvertedToTravelAgencies();
    }
}
