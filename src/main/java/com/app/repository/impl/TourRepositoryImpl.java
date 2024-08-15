package com.app.repository.impl;

import com.app.model.tour.Tour;
import com.app.repository.TourRepository;
import com.app.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TourRepositoryImpl extends AbstractCrudRepository<Tour, Integer> implements TourRepository {
    public TourRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public List<Tour> getByCountryName(String countryName) {
        var sql = "SELECT * FROM %s t join countries c on t.country_id = c.id WHERE c.name = :countryName"
                .formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("countryName", countryName)
                .mapToBean(type)
                .list());
    }

    @Override
    public List<Tour> getInPriceRange(BigDecimal from, BigDecimal to) {
        var sql = "SELECT * from %s where price_per_person >= :from and price_per_person <= :to"
                .formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("from", from)
                .bind("to", to)
                .mapToBean(type)
                .list());
    }

    @Override
    public List<Tour> getLessThanGiveNPrice(BigDecimal to) {
        var sql = "SELECT * from %s where price_per_person < :to".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("to", to)
                .mapToBean(type)
                .list());
    }

    @Override
    public List<Tour> getMoreExpensiveThanGivenPrice(BigDecimal from) {
        var sql = "SELECT * from %s where price_per_person >= :from".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("from", from)
                .mapToBean(type)
                .list());
    }

    @Override
    public List<Tour> getInDateRange(LocalDate from, LocalDate to) {
        var sql = "SELECT * from %s where start_date >= :from and end_date <= :to".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("from", from)
                .bind("to", to)
                .mapToBean(type)
                .list());
    }

    @Override
    public List<Tour> getBeforeGivenDate(LocalDate to) {
        var sql = "SELECT * from %s where end_date <= :to".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("to", to)
                .mapToBean(type)
                .list());
    }

    @Override
    public List<Tour> getAfterGivenDate(LocalDate from) {
        var sql = "SELECT * from %s where start_date >= :from".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("from", from)
                .mapToBean(type)
                .list());
    }
}
