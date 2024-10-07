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
        var sql = "SELECT * FROM %s WHERE reservation_id = :reservation_id".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("reservation_id", id)
                .map((rs, ctx) -> ReservationComponent.valueOf(rs.getString("component")))
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

    @Override
    public List<ReservationComponent> findAll() {
        var sql = " select * from reservation_components order by reservation_id desc ";
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .map((rs, ctx) -> ReservationComponent.valueOf(rs.getString("component")))
                .list());
    }
}
