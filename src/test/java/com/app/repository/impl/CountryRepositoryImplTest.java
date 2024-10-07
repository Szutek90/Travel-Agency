package com.app.repository.impl;

import com.app.extension.DbTablesEachExtension;
import com.app.model.country.Country;
import com.app.repository.CountryRepository;
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

@ExtendWith(DbTablesEachExtension.class)
@Testcontainers(disabledWithoutDocker = true)
class CountryRepositoryImplTest{
    @SuppressWarnings("resource")
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

    static CountryRepository repository;

    @BeforeAll
    static void beforeAll() {
        repository = new CountryRepositoryImpl(jdbiExtension.getJdbi());
        DbTablesEachExtension.setJdbi(jdbiExtension.getJdbi());
    }

    @Test
    @DisplayName("When adding country")
    void test1() {
        var countryToInsert = new Country(1, "Poland");
        repository.save(countryToInsert);
        var countryFromDb = repository.findById(countryToInsert.getId())
                .orElseThrow(() -> new IllegalStateException("No Country with given ID"));
        assertThat(countryFromDb).isEqualTo(countryToInsert);
        assertThat(countryFromDb.getName()).isEqualTo("Poland");
    }

    @Test
    @DisplayName("When saving collection")
    void test2() {
        var countriesToInsert = List.of(new Country(1, "Poland"),
                new Country(2, "Germany"), new Country(3, "France"));
        repository.saveAll(countriesToInsert);
        assertThat(repository.findAll()).hasSize(3);
    }

    @Test
    @DisplayName("When updating")
    void test3() {
        var countryToInsert = new Country(1, "Poland");
        var updatedCountry = new Country(2, "Germany");
        var expectedCountry = new Country(1, "Germany");
        repository.save(countryToInsert);
        assertThatCode(() -> repository.update(updatedCountry, 1))
                .doesNotThrowAnyException();
        assertThat(repository.findAll().getFirst()).isEqualTo(expectedCountry);
    }

    @Test
    @DisplayName("When finding by id")
    void test4() {
        var countriesToInsert = List.of(new Country(1, "Poland"),
                new Country(2, "Germany"), new Country(3, "France"));
        var expectedCountry = new Country(3, "France");
        repository.saveAll(countriesToInsert);
        assertThat(repository.findById(3)
                .orElseThrow(() -> new IllegalStateException("No country with given id")))
                .isEqualTo(expectedCountry);
    }

    @Test
    @DisplayName("When finding last")
    void test5() {
        var countriesToInsert = List.of(new Country(1, "Poland"),
                new Country(2, "Germany"), new Country(3, "France"));
        var expectedCountries = List.of(new Country(2, "Germany"),
                new Country(3, "France"));
        repository.saveAll(countriesToInsert);
        assertThat(repository.findLast(2)).containsExactlyInAnyOrderElementsOf(expectedCountries);
    }

    @Test
    @DisplayName("When finding all")
    void test6() {
        var countriesToInsert = List.of(new Country(1, "Poland"),
                new Country(2, "Germany"), new Country(3, "France"));
        repository.saveAll(countriesToInsert);
        assertThat(repository.findAll()).containsExactlyInAnyOrderElementsOf(countriesToInsert);
    }

    @Test
    @DisplayName("When finding all by ids")
    void test7() {
        var countriesToInsert = List.of(new Country(1, "Poland"),
                new Country(2, "Germany"), new Country(3, "France"));
        repository.saveAll(countriesToInsert);
        var ids = List.of(1, 3);
        var expectedCountries = List.of(new Country(3, "France"),
                new Country(1, "Poland"));
        assertThat(repository.findAllById(ids)).containsExactlyInAnyOrderElementsOf(expectedCountries);
    }

    @Test
    @DisplayName("When deleting by ids")
    void test8() {
        var countriesToInsert = List.of(new Country(1, "Poland"),
                new Country(2, "Germany"), new Country(3, "France"));
        repository.saveAll(countriesToInsert);
        var ids = List.of(1, 3);
        var expectedCountries = List.of(new Country(1, "Poland"),
                new Country(3, "France"));
        assertThat(repository.deleteAllyByIds(ids)).containsExactlyInAnyOrderElementsOf(expectedCountries);
    }

    @Test
    @DisplayName("When deleting all")
    void test9() {
        var countriesToInsert = List.of(new Country(1, "Poland"),
                new Country(2, "Germany"), new Country(3, "France"));
        repository.saveAll(countriesToInsert);
        repository.deleteAll();
        assertThat(repository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("When deleting by id")
    void test10() {
        var countriesToInsert = List.of(new Country(1, "Poland"),
                new Country(2, "Germany"), new Country(3, "France"));
        repository.saveAll(countriesToInsert);
        assertThat(repository.deleteById(2)).isEqualTo(new Country(2, "Germany"));
    }

    @Test
    @DisplayName("When finding by country name")
    void test11() {
        var countriesToInsert = List.of(new Country(1, "Poland"),
                new Country(2, "Germany"), new Country(3, "France"));
        repository.saveAll(countriesToInsert);
        assertThat(repository.findByCountry("Poland")
                .orElseThrow(() -> new IllegalStateException("No country")))
                .isEqualTo(new Country(1, "Poland"));
    }
}
