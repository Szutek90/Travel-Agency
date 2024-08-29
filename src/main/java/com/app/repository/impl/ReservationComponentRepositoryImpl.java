package com.app.repository.impl;

import com.app.model.ReservationComponent;
import com.app.repository.ReservationComponentRepository;
import com.app.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ReservationComponentRepositoryImpl extends AbstractCrudRepository<ReservationComponent, Integer> implements ReservationComponentRepository {
    public ReservationComponentRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public List<ReservationComponent> findByReservationId(int id) {
        var sql = "SELECT component FROM %s WHERE reservation_id=:reservation_id".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("reservation_id", id)
                .mapTo(String.class)
                .map(ReservationComponent::valueOf)
                //TODO czy to jest poprawne mapowanie: 1. do String -> 2. do Enum. Moze jest jakas szybsza metoda?
                .list());
    }

    public ReservationComponent save(int id, ReservationComponent item) {
        var sql = "insert into %s ( reservation_id, component) values ( :reservation_id, :component )"
                .formatted(tableName());
        jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("reservation_id", id)
                .bind("component", item)
                .execute());
        return item;
    }
}
