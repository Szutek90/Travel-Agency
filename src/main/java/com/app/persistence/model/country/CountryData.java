package com.app.persistence.model.country;

import com.app.model.country.Country;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.*;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "country")
@EqualsAndHashCode
public class CountryData {
    @XmlElement
    private Integer id;
    @XmlElement
    private String name;

    public Country toCountry() {
        return new Country(id, name);
    }
}
