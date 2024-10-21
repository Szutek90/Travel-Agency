package com.app.persistence.xml.deserializer.impl;

import com.app.persistence.model.tour.ToursData;
import com.app.persistence.xml.deserializer.generic.AbstractJaxbXmlFileDeserializer;
import org.springframework.stereotype.Component;

@Component
public class ToursDataXmlDeserializer extends AbstractJaxbXmlFileDeserializer<ToursData> {
}
