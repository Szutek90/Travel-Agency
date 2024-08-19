package com.app.config;

import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

@Configuration
@ComponentScan("com.app")
@PropertySource({"classpath:application.properties"})
@RequiredArgsConstructor
public class AppConfig {
    private final Environment env;

    @Bean
    public Jdbi jdbi() {
        var url = env.getRequiredProperty("db.url");
        var user = env.getRequiredProperty("db.user");
        var password = env.getRequiredProperty("db.password");

        return Jdbi.create(url, user, password);
    }

    @PostConstruct
    private void postConstruct() {
        createTables();
    }

    private void createTables() {
        var countrySql = """
                CREATE TABLE IF NOT EXISTS countries (
                    id INTEGER PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL
                );
                """;

        var personSql = """
                CREATE TABLE IF NOT EXISTS persons (
                    id INTEGER PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(255) NOT NULL,
                    surname VARCHAR(255) NOT NULL,
                    email VARCHAR(255) UNIQUE NOT NULL
                );
                """;

        var toursSql = """
                CREATE TABLE IF NOT EXISTS tours (
                    id INTEGER PRIMARY KEY AUTO_INCREMENT,
                    agencyId INTEGER NOT NULL,
                    countryId INTEGER NOT NULL,
                    pricePerPerson DECIMAL(10, 2) NOT NULL,
                    startDate DATE NOT NULL,
                    endDate DATE NOT NULL,
                    FOREIGN KEY (countryId) REFERENCES countries(id)
                );
                """;

        var reservationSql = """
                CREATE TABLE IF NOT EXISTS reservations (
                    id INTEGER PRIMARY KEY AUTO_INCREMENT,
                    tourId INTEGER NOT NULL,
                    agencyId INTEGER NOT NULL,
                    customerId INTEGER NOT NULL,
                    quantityOfPeople INTEGER NOT NULL,
                    discount INTEGER NOT NULL,
                    FOREIGN KEY (tourId) REFERENCES tours(id),
                    FOREIGN KEY (customerId) REFERENCES persons(id)
                );
                """;

        var componentsSql = """
                CREATE TABLE IF NOT EXISTS reservations_components (
                    reservationId INTEGER NOT NULL,
                    component VARCHAR(50) NOT NULL,
                    PRIMARY KEY (reservationId, component),
                    FOREIGN KEY (reservationId) REFERENCES reservations(id)
                );
                """;

        jdbi().useHandle(handle -> {
            handle.execute(countrySql);
            handle.execute(personSql);
            handle.execute(toursSql);
            handle.execute(reservationSql);
            handle.execute(componentsSql);
        });
    }

}
