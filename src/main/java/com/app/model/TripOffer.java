package com.app.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public record TripOffer(int id, int agencyId, int countryId, BigDecimal price, LocalDate from, LocalDate to) {
}
