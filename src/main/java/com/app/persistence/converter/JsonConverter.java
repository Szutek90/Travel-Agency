package com.app.persistence.converter;

import java.io.FileReader;

public interface JsonConverter<T> {
    T fromJson(FileReader fileReader, Class<T> type);
}
