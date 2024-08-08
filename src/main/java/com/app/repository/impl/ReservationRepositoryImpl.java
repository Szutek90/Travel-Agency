package com.app.repository.impl;

import com.app.model.Reservation;
import com.app.repository.ReservationRepository;
import com.app.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;

public class ReservationRepositoryImpl extends AbstractCrudRepository<Reservation, Integer> implements ReservationRepository {
    public ReservationRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }
}
