package com.app.persistence.deserializer.generic;

import com.app.persistence.converter.JsonConverter;
import com.app.persistence.deserializer.JsonDeserializer;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.io.FileReader;
import java.lang.reflect.ParameterizedType;

@RequiredArgsConstructor
public class AbstractJsonDeserializer<T> implements JsonDeserializer<T>{
    private final Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];
    private final JsonConverter<T> converter;

    @SneakyThrows
    @Override
    public T deserialize(String filename) {
        try(var fileReader = new FileReader(filename)){
            return converter.fromJson(fileReader, type);
        }
    }
}
