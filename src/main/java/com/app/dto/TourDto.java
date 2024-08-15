package com.app.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TourDto(String countryName, BigDecimal pricePerPerson, LocalDate startDate, LocalDate endDate) {
}
