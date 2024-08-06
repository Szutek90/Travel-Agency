package com.app.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Components {

    ALL_INCLUSIVE("ALL_INCLUSIVE"),
    INSURANCE("INSURANCE"),
    TRIP_A("TRIP_A"),
    TRIP_B("TRIP_B");

    private final String component;

}
