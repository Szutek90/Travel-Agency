package com.app.repository.impl;

import com.app.model.TripOffer;
import com.app.repository.TripOfferRepository;
import com.app.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class TripOfferRepositoryImpl extends AbstractCrudRepository<TripOffer, Integer> implements TripOfferRepository {
    public TripOfferRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public List<TripOffer> getByAgencyId(int agencyId) {
        var sql = "SELECT * FROM %s WHERE agency_id = :agencyId".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("agencyId", agencyId)
                .mapToBean(type)
                .list());
    }

    @Override
    public List<TripOffer> getBetweenDates(Date from, Date to) {
        var sql = "SELECT * FROM %s WHERE between :from and :to".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("from", from)
                .bind("to", to)
                .mapToBean(type)
                .list());
    }

    @Override
    public List<TripOffer> getLowerThanPrice(BigDecimal price) {
        var sql = "SELECT * FROM %s WHERE price < :price".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("price", price)
                .mapToBean(type)
                .list());
    }

    @Override
    public List<TripOffer> getUpperThanPrice(BigDecimal price) {
        var sql = "SELECT * FROM %s WHERE price > :price".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("price", price)
                .mapToBean(type)
                .list());
    }
}
