package com.app.dto;

import com.app.model.Components;
import com.app.model.agency.TravelAgency;
import com.app.model.person.Person;
import com.app.model.tour.Tour;

import java.util.List;

public record ReservationDto(Person customer, Tour tour, TravelAgency travelAgency, int quantityOfPeople, int discount, List<Components> components) {
}
