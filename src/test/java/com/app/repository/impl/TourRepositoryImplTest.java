package com.app.repository.impl;

import com.app.config.AppTestsConfig;
import com.app.converter.countries.FileToCountriesConverter;
import com.app.converter.tours.FileToToursConverter;
import com.app.extension.DbTablesEachExtension;
import com.app.model.country.Country;
import com.app.model.tour.Tour;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.testing.junit5.JdbiExtension;
import org.jdbi.v3.testing.junit5.tc.JdbiTestcontainersExtension;
import org.jdbi.v3.testing.junit5.tc.TestcontainersDatabaseInformation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(DbTablesEachExtension.class)
@Testcontainers(disabledWithoutDocker = true)
@ContextConfiguration(classes = AppTestsConfig.class)
class TourRepositoryImplTest {
    static Jdbi jdbi;
    private static TourRepositoryImpl tourRepository;
    private static CountryRepositoryImpl countryRepository;

    static List<Tour> tours = List.of(new Tour(1, 1, 1, new BigDecimal("10.00"),
                    LocalDate.of(2024, 5, 19),
                    LocalDate.of(2024, 5, 30)),
            new Tour(2, 1, 1, new BigDecimal("35.00"),
                    LocalDate.of(2024, 10, 24),
                    LocalDate.of(2024, 11, 1)));

    static List<Country> countries = List.of(Country.builder().name("Poland").id(1).build(),
            Country.builder().name("Australia").id(2).build());

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


    @BeforeAll
    static void beforeAll() {
        jdbi = jdbiExtension.getJdbi();
        var tourFilename = "toursTest.json";
        var countryFilename = "countriesTest.json";
        var format = "json";
        var contextMockito = mock(ApplicationContext.class);
        var tourConverter = mock(FileToToursConverter.class);
        var countryConverter = mock(FileToCountriesConverter.class);
        when(contextMockito.getBean("%sFileToToursConverter".formatted(format),
                FileToToursConverter.class)).thenReturn(tourConverter);
        when(contextMockito.getBean("%sFileToCountriesConverter".formatted(format),
                FileToCountriesConverter.class)).thenReturn(countryConverter);
        tourRepository = new TourRepositoryImpl(jdbi, contextMockito);
        countryRepository = new CountryRepositoryImpl(jdbi, contextMockito);
        tourRepository.filename = tourFilename;
        countryRepository.filename = countryFilename;
        tourRepository.format = format;
        countryRepository.format = format;
        DbTablesEachExtension.setJdbi(jdbi);
    }

    @BeforeEach
    void beforeEach() {
        countryRepository.saveAll(countries);
        tourRepository.saveAll(tours);
    }

    @Test
    @DisplayName("When getting by country name")
    void test1() {
        assertThat(tourRepository.getByCountryName("Poland"))
                .isEqualTo(tours);
    }

    @Test
    @DisplayName("When getting in price range")
    void test2() {
        var expected = new Tour(1, 1, 1, new BigDecimal("10.00"),
                LocalDate.of(2024, 5, 19),
                LocalDate.of(2024, 5, 30));

        assertThat(tourRepository.getInPriceRange(BigDecimal.ZERO, new BigDecimal("20.00")))
                .isEqualTo(List.of(expected));
    }

    @Test
    @DisplayName("When getting less than given price")
    void test3() {
        var expected = new Tour(1, 1, 1, new BigDecimal("10.00"),
                LocalDate.of(2024, 5, 19),
                LocalDate.of(2024, 5, 30));

        assertThat(tourRepository.getLessThanGivenPrice(new BigDecimal("20.00")))
                .isEqualTo(List.of(expected));
    }


    @Test
    @DisplayName("When getting more expensive than given price")
    void test4() {
        var expected = new Tour(2, 1, 1, new BigDecimal("35.00"),
                LocalDate.of(2024, 10, 24),
                LocalDate.of(2024, 11, 1));

        assertThat(tourRepository.getMoreExpensiveThanGivenPrice(new BigDecimal("20.00")))
                .isEqualTo(List.of(expected));
    }

    @Test
    @DisplayName("When getting in date range")
    void test5() {
        var expected = new Tour(2, 1, 1, new BigDecimal("35.00"),
                LocalDate.of(2024, 10, 24),
                LocalDate.of(2024, 11, 1));

        assertThat(tourRepository.getInDateRange(LocalDate.of(2024, 9, 20),
                LocalDate.of(2024, 12, 10)))
                .isEqualTo(List.of(expected));
    }

    @Test
    @DisplayName("When getting before given date")
    void test6() {
        var expected = new Tour(1, 1, 1, new BigDecimal("10.00"),
                LocalDate.of(2024, 5, 19),
                LocalDate.of(2024, 5, 30));
        assertThat(tourRepository.getBeforeGivenDate(LocalDate.of(2024, 6, 10)))
                .isEqualTo(List.of(expected));
    }

    @Test
    @DisplayName("When getting after given date")
    void test7() {
        var expected = new Tour(2, 1, 1, new BigDecimal("35.00"),
                LocalDate.of(2024, 10, 24),
                LocalDate.of(2024, 11, 1));
        assertThat(tourRepository.getAfterGivenDate(LocalDate.of(2024, 6, 10)))
                .isEqualTo(List.of(expected));
    }

    @Test
    @DisplayName("When getting by agency id")
    void test8() {
        var expected = List.of(new Tour(1, 1, 1, new BigDecimal("10.00"),
                        LocalDate.of(2024, 5, 19),
                        LocalDate.of(2024, 5, 30)),
                new Tour(2, 1, 1, new BigDecimal("35.00"),
                        LocalDate.of(2024, 10, 24),
                        LocalDate.of(2024, 11, 1)));

        assertThat(tourRepository.getByAgency(1)).isEqualTo(expected);
    }
}
