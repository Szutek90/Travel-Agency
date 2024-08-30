package com.app.model.reservation;

import lombok.*;

@Data
@EqualsAndHashCode
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    protected Integer id;
    protected Integer tourId;
    protected Integer agencyId;
    protected Integer customerId;
    protected Integer quantityOfPeople;
    protected Integer discount;
}
