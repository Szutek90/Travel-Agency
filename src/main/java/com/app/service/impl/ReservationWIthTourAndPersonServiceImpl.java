package com.app.service.impl;

import com.app.dto.ReservationDto;
import com.app.model.reservation.Reservation;
import com.app.model.person.PersonMapper;
import com.app.model.reservation.ReservationMapper;
import com.app.model.tour.TourMapper;
import com.app.repository.impl.PersonRepositoryImpl;
import com.app.repository.impl.ReservationRepositoryImpl;
import com.app.repository.impl.TourRepositoryImpl;
import com.app.service.ReservationWIthTourAndPersonService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReservationWIthTourAndPersonServiceImpl implements ReservationWIthTourAndPersonService {
    private final ReservationRepositoryImpl reservationRepository;
    private final TourRepositoryImpl tourRepository;
    private final PersonRepositoryImpl personRepository;

    @Override
    public void makeReservation(ReservationDto reservationDto) {
        if (tourRepository.findById(TourMapper.toId.applyAsInt(reservationDto.tour())).isEmpty()) {
            throw new IllegalStateException("tour not found");
        }
        if (personRepository
                .findById(PersonMapper.toId.applyAsInt(reservationDto.customer())).isEmpty()) {
            throw new IllegalStateException("person not found");
        }
        reservationRepository.save(Reservation.builder()
                .tourId(TourMapper.toId.applyAsInt(reservationDto.tour()))
                .customerId(PersonMapper.toId.applyAsInt(reservationDto.customer()))
                .components(reservationDto.components())
                .quantityOfPeople(reservationDto.quantityOfPeople())
                .discount(reservationDto.discount())
                .build());
    }

    @Override
    public void deleteReservation(Reservation reservation) {
        reservationRepository.deleteById(ReservationMapper.toId.applyAsInt(reservation));
    }
}
