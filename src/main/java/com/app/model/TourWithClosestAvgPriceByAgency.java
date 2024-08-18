package com.app.model;

import com.app.model.tour.Tour;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class TourWithClosestAvgPriceByAgency {
    private final BigDecimal averagePrice;
    private final Tour tour;
}
