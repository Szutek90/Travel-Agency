package com.app.model.tour;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Tour {
    protected Integer id;
    protected Integer agencyId;
    protected Integer countryId;
    protected BigDecimal pricePerPerson;
    private LocalDate startDate;
    private LocalDate endDate;
}