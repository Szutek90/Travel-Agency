package com.app.repository;

import com.app.model.ReservationComponent;
import com.app.repository.generic.CrudRepository;

import java.util.List;

public interface ReservationComponentRepository extends CrudRepository<ReservationComponent, Integer> {
    List<ReservationComponent> findByReservationId(int id);
    ReservationComponent save(int id, ReservationComponent item);
}
