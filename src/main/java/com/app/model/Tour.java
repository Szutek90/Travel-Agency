package com.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record Tour(int id, int countryId, BigDecimal pricePerPerson, LocalDate startDate, LocalDate endDate) {
}