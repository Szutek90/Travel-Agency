package com.app.persistence.json.deserializer;

public interface JsonDeserializer <T>{
    T deserialize(String filename);
}
