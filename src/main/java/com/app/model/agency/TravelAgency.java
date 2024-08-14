package com.app.model.agency;

import lombok.*;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class TravelAgency {
    final int id;
    final String name;
    final String city;
    private final String phoneNumber;
}
