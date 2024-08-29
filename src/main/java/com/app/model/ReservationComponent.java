package com.app.model;

import lombok.Getter;

@Getter
public enum ReservationComponent {

    ALL_INCLUSIVE("ALL_INCLUSIVE"),
    INSURANCE("INSURANCE"),
    TRIP_A("TRIP_A"),
    TRIP_B("TRIP_B");

    private final String component;

    ReservationComponent(String component) {
        this.component = component;
    }
}
