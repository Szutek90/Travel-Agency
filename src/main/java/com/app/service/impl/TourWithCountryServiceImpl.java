package com.app.service.impl;

import com.app.dto.TourDto;
import com.app.model.country.CountryMapper;
import com.app.model.tour.Tour;
import com.app.repository.impl.CountryRepositoryImpl;
import com.app.repository.impl.TourRepositoryImpl;
import com.app.service.TourWithCountryService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class TourWithCountryServiceImpl implements TourWithCountryService {
    private final TourRepositoryImpl tourRepositoryImpl;
    private final CountryRepositoryImpl countryRepositoryImpl;

    @Override
    public Tour getById(int id) {
        return tourRepositoryImpl.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no Tour with given id"));
    }

    @Override
    public List<Tour> getByCountry(String country) {
        return tourRepositoryImpl.getByCountryName(country);
    }

    @Override
    public List<Tour> getToursInPriceRange(BigDecimal from, BigDecimal to) {
        return tourRepositoryImpl.getInPriceRange(from, to);
    }

    @Override
    public List<Tour> getToursCheaperThan(BigDecimal priceTo) {
        return tourRepositoryImpl.getLessThanGiveNPrice(priceTo);
    }

    @Override
    public List<Tour> getToursMoreExpensiveThan(BigDecimal priceFrom) {
        return tourRepositoryImpl.getMoreExpensiveThanGivenPrice(priceFrom);
    }

    @Override
    public List<Tour> getToursInRange(LocalDate from, LocalDate to) {
        return tourRepositoryImpl.getInDateRange(from, to);
    }

    @Override
    public List<Tour> getToursAfterDate(LocalDate from) {
        return tourRepositoryImpl.getAfterGivenDate(from);
    }

    @Override
    public List<Tour> getToursBeforeDate(LocalDate to) {
        return tourRepositoryImpl.getBeforeGivenDate(to);
    }

    @Override
    public Tour createTour(TourDto tourDto) {
        var countryToAdd = countryRepositoryImpl.findByCountry(tourDto.countryName())
                .orElseThrow(() -> new IllegalArgumentException("There is no country with given name"));

        var tourToSave = tourRepositoryImpl.save(Tour.builder()
                .countryId(CountryMapper.toId.applyAsInt(countryToAdd))
                .pricePerPerson(tourDto.pricePerPerson())
                .startDate(tourDto.startDate())
                .endDate(tourDto.endDate())
                .build());
        tourRepositoryImpl.save(tourToSave);
        return tourToSave;
    }
}
