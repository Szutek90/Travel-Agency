package com.app.converter.tours.impl;

import com.app.converter.tours.FileToToursConverter;
import com.app.model.tour.Tour;
import com.app.persistence.json.deserializer.JsonDeserializer;
import com.app.persistence.model.tour.ToursData;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JsonFileToToursConverterImpl implements FileToToursConverter {
    private final JsonDeserializer<ToursData> deserializer;

    @Override
    public List<Tour> convert(String filename) {
        return deserializer.deserialize(filename).getConvertedToTours();
    }
}
