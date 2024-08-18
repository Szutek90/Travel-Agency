package com.app.model.reservation;

import com.app.model.Components;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@EqualsAndHashCode
@ToString
@Builder
@AllArgsConstructor
public class Reservation {
    protected final Integer id;
    protected final Integer tourId;
    protected final Integer agencyId;
    private final Integer customerId;
    protected final int quantityOfPeople;
    private final int discount;
    private final List<Components> components;
}
