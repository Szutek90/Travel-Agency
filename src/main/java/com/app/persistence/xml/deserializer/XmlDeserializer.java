package com.app.persistence.xml.deserializer;

public interface XmlDeserializer <T>{
    T deserialize(String xml);
}
