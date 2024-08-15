package com.app.model.tour;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tour {
    private int id;
    private int countryId;
    private BigDecimal pricePerPerson;
    private LocalDate startDate;
    private LocalDate endDate;
}