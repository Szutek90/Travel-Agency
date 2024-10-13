package com.app.persistence.model.agency;

import com.app.model.agency.TravelAgency;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@XmlRootElement(name = "travelAgencies")
public class TravelAgenciesData {

    private List<TravelAgencyData> travelAgencies;

    @XmlElement(name = "travelAgency")
    public List<TravelAgencyData> getTravelAgencies() {
        return travelAgencies;
    }

    public List<TravelAgency> getConvertedToTravelAgencies() {
        return travelAgencies.stream()
                .map(TravelAgencyData::toTravelAgency)
                .toList();
    }
}
