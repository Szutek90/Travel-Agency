package com.app.model.agency;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class TravelAgency {
    final int id;
    final String name;
    final String city;
    final String phoneNumber;
}