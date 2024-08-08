package com.app.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class TripOffer {
    private int id;
    private int agencyId;
    private int countryId;
    private BigDecimal price;
    private LocalDate from;
    private LocalDate to;
}
