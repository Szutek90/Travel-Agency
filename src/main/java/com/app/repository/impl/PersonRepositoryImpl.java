package com.app.repository.impl;

import com.app.model.person.Person;
import com.app.repository.PersonRepository;
import com.app.repository.generic.AbstractCrudRepository;
import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PersonRepositoryImpl extends AbstractCrudRepository<Person, Integer> implements PersonRepository {

    public PersonRepositoryImpl(Jdbi jdbi) {
        super(jdbi);
    }

    @Override
    public List<Person> findBySurname(String surname) {
        var sql = "SELECT * FROM %s WHERE surname = :surname".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("surname", surname)
                .mapToBean(type)
                .list());
    }

    @Override
    public Optional<Person> findByNameAndSurname(String name, String surname) {
        var sql = "SELECT * FROM %s WHERE name = :name AND surname = :surname "
                .formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("name", name)
                .bind("surname", surname)
                .mapToBean(type)
                .findFirst());
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        var sql = "SELECT * FROM %s WHERE email = :email".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("email", email)
                .mapToBean(type)
                .findFirst());
    }
}
