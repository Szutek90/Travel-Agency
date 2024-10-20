package com.app.config;

import com.app.persistence.json.deserializer.custom.LocalDateDeserializer;
import com.app.persistence.json.deserializer.custom.LocalDateSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.time.LocalDate;

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
        var jdbi = Jdbi.create(url, user, password);
        createTables(jdbi);
        return jdbi;
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .registerTypeAdapter(LocalDate.class, new LocalDateSerializer())
                .setPrettyPrinting().create();
    }

    private void createTables(Jdbi jdbi) {
        var countrySql = """
                CREATE TABLE IF NOT EXISTS countries (
                    id INTEGER PRIMARY KEY AUTO_INCREMENT,
                    name VARCHAR(255) UNIQUE NOT NULL
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
                    agency_id INTEGER NOT NULL,
                    country_id INTEGER NOT NULL,
                    price_per_person DECIMAL(10, 2) NOT NULL,
                    start_date DATE NOT NULL,
                    end_date DATE NOT NULL,
                    FOREIGN KEY (country_id) REFERENCES countries(id)
                );
                """;

        var reservationSql = """
                CREATE TABLE IF NOT EXISTS reservations (
                    id INTEGER PRIMARY KEY AUTO_INCREMENT,
                    tour_id INTEGER NOT NULL,
                    agency_id INTEGER NOT NULL,
                    customer_id INTEGER NOT NULL,
                    quantity_of_people INTEGER NOT NULL,
                    discount INTEGER NOT NULL,
                    FOREIGN KEY (tour_id) REFERENCES tours(id),
                    FOREIGN KEY (customer_id) REFERENCES persons(id)
                );
                """;

        var componentsSql = """
                CREATE TABLE IF NOT EXISTS reservation_components (
                    reservation_id INTEGER NOT NULL,
                    component VARCHAR(50) NOT NULL,
                    PRIMARY KEY (reservation_id, component),
                    FOREIGN KEY (reservation_id) REFERENCES reservations(id) ON DELETE CASCADE
                );
                """;

        jdbi.useHandle(handle -> handle.inTransaction(transactionHandle -> {
            transactionHandle.execute(countrySql);
            transactionHandle.execute(personSql);
            transactionHandle.execute(toursSql);
            transactionHandle.execute(reservationSql);
            transactionHandle.execute(componentsSql);
            return null;
        }));

    }
    }
