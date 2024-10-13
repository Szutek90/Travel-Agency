package com.app.model.tour;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class Tour {
    protected final Integer id;
    protected final Integer agencyId;
    protected final Integer countryId;
    protected final BigDecimal pricePerPerson;
    private final LocalDate startDate;
    private final LocalDate endDate;
}