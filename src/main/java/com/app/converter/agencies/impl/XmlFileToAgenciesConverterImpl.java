package com.app.converter.agencies.impl;

import com.app.converter.agencies.FileToAgenciesConverter;
import com.app.model.agency.TravelAgency;
import com.app.persistence.model.agency.TravelAgenciesData;
import com.app.persistence.xml.deserializer.XmlFileDeserializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.List;

@Component
@RequiredArgsConstructor
public class XmlFileToAgenciesConverterImpl implements FileToAgenciesConverter {
    private final XmlFileDeserializer<TravelAgenciesData> deserializer;

    @Override
    public List<TravelAgency> convert(String filename) {
        return deserializer.deserializeFromFile(Paths.get(filename)).getConvertedToTravelAgencies();
    }
}
