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
 //   protected final List<Components> components;
// TODO [ 5 ] czy pole components powinno być w tej klasie? W Bazie danych jest oddzielna tabela components,
//  ktora ma relacje wiele do wielu ( w tabeli components jest id_rezerwacji oraz component )
}
