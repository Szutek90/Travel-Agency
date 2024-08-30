package com.app.repository.impl;

import com.app.model.agency.TravelAgency;
import com.app.model.agency.TravelAgencyMapper;
import com.app.repository.TravelAgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TravelAgencyRepositoryImpl implements TravelAgencyRepository {
    private final List<TravelAgency> travelAgencies;

    @Autowired
    public TravelAgencyRepositoryImpl(List<TravelAgency> travelAgencies) {
        this.travelAgencies = travelAgencies;
    }

    public TravelAgencyRepositoryImpl() {
        this.travelAgencies = new ArrayList<>();
    }

    @Override
    public Optional<TravelAgency> findById(int id) {
        return travelAgencies.stream()
                .filter(t -> TravelAgencyMapper.toId.applyAsInt(t) == id)
                .findFirst();
    }

    @Override
    public Optional<TravelAgency> findByName(String name) {
        return travelAgencies.stream()
                .filter(t -> Objects.equals(TravelAgencyMapper.toName.apply(t), name))
                .findFirst();
    }

    @Override
    public List<TravelAgency> findByCity(String city) {
        return travelAgencies.stream()
                .filter(t -> Objects.equals(TravelAgencyMapper.toCity.apply(t), city))
                .toList();
    }

    @Override
    public List<TravelAgency> getAll() {
        return travelAgencies;
    }

    @Override
    public int save(TravelAgency travelAgency) {
        this.travelAgencies.add(travelAgency);
        return TravelAgencyMapper.toId.applyAsInt(travelAgency);
    }

    @Override
    public List<TravelAgency> saveAll(List<TravelAgency> travelAgencies) {
        var currentIds = generateIds(getAll());
        for (var agency : travelAgencies) {
            if (currentIds.contains(TravelAgencyMapper.toId.applyAsInt(agency))) {
                throw new IllegalArgumentException("Duplicate id: " + agency);
            }
        }
        this.travelAgencies.addAll(travelAgencies);
        return travelAgencies;
    }

    @Override
    public TravelAgency delete(int id) {
        var agencyToDelete = findById(id)
                .orElseThrow(() -> new RuntimeException("TravelAgency not found"));
        travelAgencies.remove(agencyToDelete);
        return agencyToDelete;
    }

    private List<Integer> generateIds(List<TravelAgency> travelAgencies) {
        return travelAgencies.stream()
                .map(TravelAgencyMapper.toId::applyAsInt)
                .toList();
    }
}
