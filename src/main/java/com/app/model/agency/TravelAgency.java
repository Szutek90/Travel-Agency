package com.app.model.agency;

import lombok.*;

@EqualsAndHashCode
@ToString
@AllArgsConstructor
public class TravelAgency {
    final Integer id;
    final String name;
    final String city;
    private final String phoneNumber;
}
