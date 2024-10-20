package com.app.repository.impl;

import com.app.model.tour.Tour;
import com.app.persistence.json.deserializer.JsonDeserializer;
import com.app.persistence.model.tour.ToursData;
import com.app.repository.TourRepository;
import com.app.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public class TourRepositoryImpl extends AbstractCrudRepository<Tour, Integer> implements TourRepository {
    private final JsonDeserializer<ToursData> deserializer;

    @Value("${tours.file}")
    private String filename;

    public TourRepositoryImpl(Jdbi jdbi, JsonDeserializer<ToursData> deserializer) {
        super(jdbi);
        this.deserializer = deserializer;
    }

    @PostConstruct
    public void init() {
        if (findAll().isEmpty()) {
            saveAll(deserializer.deserialize(filename).getConvertedToTours());
        }
    }

    @Override
    public List<Tour> getByCountryName(String countryName) {
        var sql = "SELECT * FROM tours t WHERE t.country_id = (SELECT id FROM countries WHERE name = :countryName)";
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
    public List<Tour> getLessThanGivenPrice(BigDecimal to) {
        var sql = "SELECT * from %s where price_per_person <= :to".formatted(tableName());
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

    @Override
    public List<Tour> getByAgency(int agencyId) {
        var sql = "SELECT * from %s where agency_id = :agencyId".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("agencyId", agencyId)
                .mapToBean(type)
                .list());
    }
}
