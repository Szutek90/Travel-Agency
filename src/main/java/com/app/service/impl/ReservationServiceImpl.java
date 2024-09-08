package com.app.service.impl;

import com.app.dto.reservation.GetReservationDto;
import com.app.dto.reservation.CreateReservationDto;
import com.app.model.TourWithClosestAvgPriceByAgency;
import com.app.model.agency.TravelAgency;
import com.app.model.agency.TravelAgencyMapper;
import com.app.model.country.Country;
import com.app.model.reservation.Reservation;
import com.app.model.person.PersonMapper;
import com.app.model.reservation.ReservationMapper;
import com.app.model.tour.Tour;
import com.app.model.tour.TourMapper;
import com.app.repository.*;
import com.app.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final TourRepository tourRepository;
    private final PersonRepository personRepository;
    private final TravelAgencyRepository travelAgencyRepository;
    private final CountryRepository countryRepository;
    private final ReservationComponentRepository componentRepository;

    @Override
    public void makeReservation(CreateReservationDto createReservationDto) {
        if (tourRepository.findById(TourMapper.toId.applyAsInt(createReservationDto.tour())).isEmpty()) {
            throw new IllegalStateException("tour not found");
        }
        if (personRepository
                .findById(PersonMapper.toId.applyAsInt(createReservationDto.customer())).isEmpty()) {
            throw new IllegalStateException("person not found");
        }

        var reservation = reservationRepository.save(
                Reservation.builder()
                        .tourId(createReservationDto.tour().getId())
                        .customerId(createReservationDto.customer().getId())
                        .agencyId(TravelAgencyMapper.toId.applyAsInt(createReservationDto.travelAgency()))
                        .quantityOfPeople(createReservationDto.quantityOfPeople())
                        .discount(createReservationDto.discount())
                        .build());
        var reservationId = ReservationMapper.toId.applyAsInt(reservation);
        var components = createReservationDto.reservationComponents();
        for (var component : components) {
            componentRepository.save(reservationId, component);
        }
    }

    @Override
    public void deleteReservation(int id) {
        reservationRepository.deleteById(id);
    }

    @Override
    public List<GetReservationDto> getAllReservations() {
        var allReservations = reservationRepository.findAll();
        return allReservations
                .stream()
                .map(this::toGetReservationDto)
                .toList();
    }

    @Override
    public List<TravelAgency> getAgencyWithMostOrganizedTrips() {
        return reservationRepository.findAll().stream()
                .collect(Collectors.groupingBy(ReservationMapper.toId::applyAsInt,
                        Collectors.mapping(e ->
                                        travelAgencyRepository.findById(ReservationMapper.toAgencyId.applyAsInt(e))
                                                .orElseThrow(() ->
                                                        new IllegalStateException("Travel Agency not found")),
                                Collectors.toList())))
                .entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow();
    }

    @Override
    public List<TravelAgency> getAgencyEarnMostMoney() {
        return reservationRepository.findAll().stream()
                .collect(Collectors.groupingBy(e -> TourMapper.toPrice
                                .apply(tourRepository.findById(ReservationMapper.toTourId.applyAsInt(e))
                                        .orElseThrow())
                                .multiply(BigDecimal.valueOf(ReservationMapper.toQuantityOfPeople
                                        .applyAsInt(e))),
                        Collectors.mapping(e ->
                                        travelAgencyRepository.findById(ReservationMapper.toAgencyId.applyAsInt(e))
                                                .orElseThrow(() ->
                                                        new IllegalStateException("Travel Agency not found")),
                                Collectors.toList())))
                .entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow();
    }

    @Override
    public List<Country> getMostVisitedCountries() {
        var countriesWithChoicesNum = reservationRepository.findAll().stream()
                .collect(Collectors.groupingBy(e -> countryRepository
                                .findById(TourMapper.toCountryId.applyAsInt(tourRepository
                                        .findById(ReservationMapper.toTourId.applyAsInt(e)).orElseThrow())).orElseThrow(),
                        Collectors.counting()));
        return countriesWithChoicesNum.entrySet().stream()
                .collect(Collectors.groupingBy(Map.Entry::getValue,
                        Collectors.mapping(
                                Map.Entry::getKey,
                                Collectors.toList()
                        ))).entrySet().stream()
                .max(Map.Entry.comparingByKey())
                .map(Map.Entry::getValue)
                .orElseThrow();
    }

    @Override
    public Map<TravelAgency, TourWithClosestAvgPriceByAgency> getSummaryByTourAvgPrice() {
        var averagePriceByAgency = tourRepository.findAll().stream()
                .collect(Collectors.groupingBy(TourMapper.toAgencyId::applyAsInt,
                        Collectors.mapping(TourMapper.toPrice, Collectors.toList())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                        e -> e.getValue().stream()
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                                .divide(new BigDecimal(e.getValue().size()), RoundingMode.HALF_UP)));
        return averagePriceByAgency.entrySet().stream()
                .collect(Collectors.toMap(e -> travelAgencyRepository.findById(e.getKey()).orElseThrow(), e -> {
                    var closest = tourRepository.findAll().stream()
                            .filter(t -> TourMapper.toAgencyId.applyAsInt(t) == e.getKey())
                            .min(Comparator.comparing(t -> TourMapper.toPrice.apply(t).subtract(e.getValue()).abs()))
                            .orElseThrow();
                    return new TourWithClosestAvgPriceByAgency(e.getValue(), closest);
                }));
    }

    @Override
    public List<Tour> getToursTakingPlaceInGivenCountry(List<String> countryNames) {
        var tours = new ArrayList<Tour>();
        for (String countryName : countryNames) {
            tours.addAll(tourRepository.getByCountryName(countryName));
        }
        return tours;
    }

    @Override
    public GetReservationDto toGetReservationDto(Reservation reservation) {
        return new GetReservationDto(
                tourRepository.findById(ReservationMapper.toTourId.applyAsInt(reservation))
                        .orElseThrow(() -> new IllegalArgumentException("No tour with given id")),
                travelAgencyRepository.findById(ReservationMapper.toAgencyId.applyAsInt(reservation))
                        .orElseThrow(() -> new IllegalArgumentException("No agency with given id")),
                personRepository.findById(ReservationMapper.toPersonId.applyAsInt(reservation))
                        .orElseThrow(() -> new IllegalArgumentException("No person with given id")),
                ReservationMapper.toQuantityOfPeople.applyAsInt(reservation),
                ReservationMapper.toDiscount.applyAsInt(reservation),
                componentRepository
                        .findByReservationId(ReservationMapper.toId.applyAsInt(reservation)));
    }

}