package com.app.model.agency;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TravelAgency {
    private int id;
    private String name;
    private String city;
    private String phoneNumber;
}
