package com.app.persistence.xml.deserializer.generic;

import com.app.persistence.xml.deserializer.XmlFileDeserializer;
import jakarta.xml.bind.JAXBContext;

import java.io.StringReader;
import java.lang.reflect.ParameterizedType;

public abstract class AbstractJaxbXmlFileDeserializer<T> implements XmlFileDeserializer<T> {
    private final Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    @Override
    public T deserialize(String xml) {
        try{
            var context = JAXBContext.newInstance(type);
            var unmarshaller = context.createUnmarshaller();
            var strReader = new StringReader(xml);
            return type.cast(unmarshaller.unmarshal(strReader));
        }
        catch (Exception e){
            throw new IllegalStateException(e);
        }
    }
}
