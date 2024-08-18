package com.app.service.impl;

import com.app.dto.TourDto;
import com.app.model.country.CountryMapper;
import com.app.model.tour.Tour;
import com.app.repository.CountryRepository;
import com.app.repository.TourRepository;
import com.app.service.TourWithCountryService;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
public class TourWithCountryServiceImpl implements TourWithCountryService {
    private final TourRepository tourRepository;
    private final CountryRepository countryRepository;

    @Override
    public Tour getById(int id) {
        return tourRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("There is no Tour with given id"));
    }

    @Override
    public List<Tour> getByCountry(String country) {
        return tourRepository.getByCountryName(country);
    }

    @Override
    public List<Tour> getToursInPriceRange(BigDecimal from, BigDecimal to) {
        return tourRepository.getInPriceRange(from, to);
    }

    @Override
    public List<Tour> getToursCheaperThan(BigDecimal priceTo) {
        return tourRepository.getLessThanGiveNPrice(priceTo);
    }

    @Override
    public List<Tour> getToursMoreExpensiveThan(BigDecimal priceFrom) {
        return tourRepository.getMoreExpensiveThanGivenPrice(priceFrom);
    }

    @Override
    public List<Tour> getToursInRange(LocalDate from, LocalDate to) {
        return tourRepository.getInDateRange(from, to);
    }

    @Override
    public List<Tour> getToursAfterDate(LocalDate from) {
        return tourRepository.getAfterGivenDate(from);
    }

    @Override
    public List<Tour> getToursBeforeDate(LocalDate to) {
        return tourRepository.getBeforeGivenDate(to);
    }

    @Override
    public Tour createTour(TourDto tourDto) {
        var countryToAdd = countryRepository.findByCountry(tourDto.countryName())
                .orElseThrow(() -> new IllegalArgumentException("There is no country with given name"));

        var tourToSave = tourRepository.save(Tour.builder()
                .countryId(CountryMapper.toId.applyAsInt(countryToAdd))
                .pricePerPerson(tourDto.pricePerPerson())
                .startDate(tourDto.startDate())
                .endDate(tourDto.endDate())
                .build());
        tourRepository.save(tourToSave);
        return tourToSave;
    }
}
