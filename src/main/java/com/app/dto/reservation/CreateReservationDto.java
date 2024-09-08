package com.app.dto.reservation;

import com.app.model.ReservationComponent;
import com.app.model.agency.TravelAgency;
import com.app.model.agency.TravelAgencyMapper;
import com.app.model.person.Person;
import com.app.model.reservation.Reservation;
import com.app.model.tour.Tour;

import java.util.List;

public record CreateReservationDto(Person customer,
                                   Tour tour,
                                   TravelAgency travelAgency,
                                   int quantityOfPeople,
                                   int discount,
                                   List<ReservationComponent> reservationComponents) {

    public Reservation toReservation() {
        return Reservation.builder()
                .id(null)
                .tourId(tour.getId())
                .customerId(customer.getId())
                .quantityOfPeople(quantityOfPeople)
                .discount(discount)
                .agencyId(TravelAgencyMapper.toId.applyAsInt(travelAgency))
                .build();
    }
}
