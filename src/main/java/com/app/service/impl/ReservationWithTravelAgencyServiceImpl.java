package com.app.service.impl;

import com.app.model.agency.TravelAgency;
import com.app.model.reservation.ReservationMapper;
import com.app.repository.impl.ReservationRepositoryImpl;
import com.app.repository.impl.TravelAgencyRepositoryImpl;
import com.app.service.ReservationWithTravelAgencyService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ReservationWithTravelAgencyServiceImpl implements ReservationWithTravelAgencyService {
    private final ReservationRepositoryImpl reservationRepository;
    private final TravelAgencyRepositoryImpl travelAgencyRepository;

    @Override
    public List<TravelAgency> getAgencyWIthMostOrganizedTrips() {
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
}
