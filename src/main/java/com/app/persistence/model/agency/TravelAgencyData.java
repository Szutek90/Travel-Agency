package com.app.persistence.model.agency;

import com.app.model.agency.TravelAgency;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@NoArgsConstructor
@AllArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "travelAgency")
public class TravelAgencyData {
    @XmlElement
    private Integer id;
    @XmlElement
    private String name;
    @XmlElement
    private String city;
    @XmlElement
    private String phoneNumber;

    public TravelAgency toTravelAgency(){
        return new TravelAgency(id, name, city, phoneNumber);
    }
}
