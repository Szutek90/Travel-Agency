package com.app.converter.countries.impl;

import com.app.converter.countries.FileToCountriesConverter;
import com.app.model.country.Country;
import com.app.persistence.model.country.CountriesData;
import com.app.persistence.xml.deserializer.XmlFileDeserializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;
import java.util.List;

@Component
@RequiredArgsConstructor
public class XmlFileToCountriesConverter implements FileToCountriesConverter {
    private final XmlFileDeserializer<CountriesData> deserializer;

    @Override
    public List<Country> convert(String filename) {
        return deserializer.deserializeFromFile(Paths.get(filename)).getConvertedToCountries();
    }
}
