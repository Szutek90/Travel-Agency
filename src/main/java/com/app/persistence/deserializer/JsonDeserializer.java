package com.app.persistence.deserializer;

public interface JsonDeserializer <T>{
    T deserialize(String filename);
}
