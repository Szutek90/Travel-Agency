package com.app.persistence.model.country;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "countries")
public class CountriesData {
    private List<CountryData> countries;


    @XmlElement(name = "country")
    public List<CountryData> getCountries() {
        return countries;
    }

}