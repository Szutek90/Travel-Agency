package com.app.service;

import com.app.dto.TourDto;
import com.app.model.tour.Tour;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface TourWithCountryService {
    Tour getById(int id);

    List<Tour> getByCountry(String country);

    List<Tour> getToursInPriceRange(BigDecimal from, BigDecimal to);

    List<Tour> getToursCheaperThan(BigDecimal priceTo);

    List<Tour> getToursMoreExpensiveThan(BigDecimal priceFrom);

    List<Tour> getToursInRange(LocalDate from, LocalDate to);

    List<Tour> getToursAfterDate(LocalDate from);

    List<Tour> getToursBeforeDate(LocalDate to);

    Tour createTour(TourDto tourDto);
}
