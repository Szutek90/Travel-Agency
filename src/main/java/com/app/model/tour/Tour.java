package com.app.model.tour;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Tour {
    private int id;
    private int countryId;
    private BigDecimal pricePerPerson;
    private LocalDate startDate;
    private LocalDate endDate;
}