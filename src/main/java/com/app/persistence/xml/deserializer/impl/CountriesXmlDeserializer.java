package com.app.persistence.xml.deserializer.impl;

import com.app.persistence.model.country.CountriesData;
import com.app.persistence.xml.deserializer.generic.AbstractJaxbXmlFileDeserializer;
import org.springframework.stereotype.Component;

@Component
public class CountriesXmlDeserializer extends AbstractJaxbXmlFileDeserializer<CountriesData> {
}
