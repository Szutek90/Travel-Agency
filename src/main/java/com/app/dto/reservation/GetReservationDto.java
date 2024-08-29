package com.app.dto.reservation;

import com.app.model.ReservationComponent;
import com.app.model.agency.TravelAgency;
import com.app.model.person.Person;
import com.app.model.tour.Tour;

import java.util.List;

public record GetReservationDto(Tour tour, TravelAgency travelAgency, Person customer, int quantityOfPeople
        , int discount, List<ReservationComponent> reservationComponents) {
}
