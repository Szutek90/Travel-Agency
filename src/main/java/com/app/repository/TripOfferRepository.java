package com.app.repository;

import com.app.model.TripOffer;
import com.app.repository.generic.CrudRepository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TripOfferRepository extends CrudRepository<TripOffer, Integer> {
    List<TripOffer> getByAgencyId(int agencyId);
    List<TripOffer> getBetweenDates(Date from, Date to);
    List<TripOffer> getLowerThanPrice(BigDecimal price);
    List<TripOffer> getUpperThanPrice(BigDecimal price);
}
