package com.app.model.reservation;

import com.app.model.Components;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.List;

@Builder
@AllArgsConstructor
public class Reservation {
    protected final int id;
    private final int tourId;
    protected final int agencyId;
    private final int customerId;
    private final int quantityOfPeople;
    private final int discount;
    private final List<Components> components;
}
