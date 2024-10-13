package com.app.persistence.model.tour;

import com.app.model.tour.Tour;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Setter
@XmlRootElement(name = "tours")
public class ToursData {
    private List<TourData> tours;

    @XmlElement(name = "tour")
    public List<TourData> getTours() {
        return tours;
    }

    public List<Tour> getConvertedToTours() {
        return tours.stream()
                .map(TourData::toTour)
                .toList();
    }
}
