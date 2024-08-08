package com.app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TourDto(int countryId, BigDecimal pricePerPerson, LocalDate startDate, LocalDate endDate) {
}
