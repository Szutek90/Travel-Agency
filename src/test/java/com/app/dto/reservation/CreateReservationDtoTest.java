package com.app.dto.reservation;

import com.app.model.ReservationComponent;
import com.app.model.agency.TravelAgency;
import com.app.model.person.Person;
import com.app.model.reservation.Reservation;
import com.app.model.tour.Tour;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

class CreateReservationDtoTest {

    @Test
    @DisplayName("When converting to Reservation")
    void test1() {
        var person = Person.builder().name("jan").surname("kowalski").id(1)
                .email("jkowal@wp.pl").build();
        var createReservationDto = new CreateReservationDto(person,
                new Tour(1, 1, 1, BigDecimal.ONE, LocalDate.now(),
                        LocalDate.now()),
                new TravelAgency(1, "Agency", "Warszawa", "123456"),
                10, 0, List.of(ReservationComponent.ALL_INCLUSIVE));
        var reservation = Reservation.builder()
                .id(null)
                .customerId(1)
                .agencyId(1)
                .tourId(1)
                .quantityOfPeople(10)
                .discount(0)
                .build();
        Assertions.assertThat(createReservationDto.toReservation()).isEqualTo(reservation);

    }
}
