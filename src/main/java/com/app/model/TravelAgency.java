package com.app.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TravelAgency {
    final int id;
    final String name;
    final String city;
    final String phoneNumber;
    final List<Tour> tours;
}