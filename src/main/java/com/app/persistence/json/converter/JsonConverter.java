package com.app.persistence.json.converter;

import java.io.FileReader;

public interface JsonConverter<T> {
    T fromJson(FileReader fileReader, Class<T> type);
}
