package com.app.repository.impl;

import com.app.extension.DbTablesEachExtension;
import com.app.model.person.Person;
import com.app.repository.PersonRepository;
import org.jdbi.v3.testing.junit5.JdbiExtension;
import org.jdbi.v3.testing.junit5.tc.JdbiTestcontainersExtension;
import org.jdbi.v3.testing.junit5.tc.TestcontainersDatabaseInformation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SuppressWarnings("resource")
@ExtendWith(DbTablesEachExtension.class)
@Testcontainers(disabledWithoutDocker = true)
class PersonRepositoryImplTest {
    @Container
    static MySQLContainer<?> mySQLContainer = new MySQLContainer<>("mysql:latest")
            .withUsername("user")
            .withPassword("user1234")
            .withDatabaseName("test_db")
            .withInitScript("scripts/init.sql");

    static TestcontainersDatabaseInformation mySql = TestcontainersDatabaseInformation.of(
            mySQLContainer.getUsername(),
            mySQLContainer.getDatabaseName(),
            mySQLContainer.getPassword(),
            (catalogName, schemaName) -> String.format("create database if not exists %s", catalogName)
    );

    @RegisterExtension
    public static JdbiExtension jdbiExtension = JdbiTestcontainersExtension
            .instance(mySql, mySQLContainer);
    static PersonRepository repository;

    @BeforeAll
    static void beforeAll() {
        var jdbi = jdbiExtension.getJdbi();
        repository = new PersonRepositoryImpl(jdbi);
        DbTablesEachExtension.setJdbi(jdbi);
    }

    @Test
    @DisplayName("When finding by surname")
    void test1() {
        var personsToAdd = List.of(new Person(1, "Jan", "Kowalski", "jan@wp.pl"),
                new Person(2, "Andrzej", "Kowalski", "a@wp.pl"),
                new Person(3, "Piotr", "Kwiatkowski", "kwiatek@wp.pl"));
        repository.saveAll(personsToAdd);
        var expected = List.of(new Person(1, "Jan", "Kowalski", "jan@wp.pl"),
                new Person(2, "Andrzej", "Kowalski", "a@wp.pl"));
        assertThat(repository.findBySurname("Kowalski"))
                .containsExactlyInAnyOrderElementsOf(expected);
    }

    @Test
    @DisplayName("When finding by name and surname")
    void test2() {
        var personsToAdd = List.of(new Person(1, "Jan", "Kowalski", "jan@wp.pl"),
                new Person(2, "Andrzej", "Kowalski", "a@wp.pl"),
                new Person(3, "Piotr", "Kwiatkowski", "kwiatek@wp.pl"));
        repository.saveAll(personsToAdd);
        var expected = new Person(3, "Piotr", "Kwiatkowski", "kwiatek@wp.pl");
        assertThat(repository.findByNameAndSurname("Piotr", "Kwiatkowski")
                .orElseThrow(() -> new IllegalArgumentException("No person with given name and surname")))
                .isEqualTo(expected);
    }

    @Test
    @DisplayName("When finding by email")
    void test3() {
        var personsToAdd = List.of(new Person(1, "Jan", "Kowalski", "jan@wp.pl"),
                new Person(2, "Andrzej", "Kowalski", "a@wp.pl"),
                new Person(3, "Piotr", "Kwiatkowski", "kwiatek@wp.pl"));
        repository.saveAll(personsToAdd);
        var expected = new Person(1, "Jan", "Kowalski", "jan@wp.pl");
        assertThat(repository.findByEmail("jan@wp.pl")
                .orElseThrow(() -> new IllegalArgumentException("No person with given name and surname")))
                .isEqualTo(expected);
    }
}
