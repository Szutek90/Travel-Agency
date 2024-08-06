package com.app.persistence.converter.impl;

import com.app.model.TravelAgencies;
import com.app.persistence.converter.JsonConverter;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;

import java.io.FileReader;

@RequiredArgsConstructor
public class GsonConverter implements JsonConverter<TravelAgencies> {
    private final Gson gson;

    @Override
    public TravelAgencies fromJson(FileReader fileReader, Class<TravelAgencies> type) {
        return gson.fromJson(fileReader, type);
    }
}
