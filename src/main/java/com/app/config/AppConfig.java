package com.app.config;

import com.app.model.country.Country;
import com.app.model.tour.Tour;
import com.app.persistence.converter.impl.CountriesGsonConverter;
import com.app.persistence.converter.impl.ToursGsonConverter;
import com.app.persistence.converter.impl.TravelAgenciesGsonConverter;
import com.app.persistence.deserializer.custom.LocalDateDeserializer;
import com.app.persistence.deserializer.custom.LocalDateSerializer;
import com.app.persistence.deserializer.impl.CountriesDeserializer;
import com.app.persistence.deserializer.impl.ToursDeserializer;
import com.app.persistence.deserializer.impl.TravelAgenciesDeserializer;
import com.app.repository.TravelAgencyRepository;
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
    private final TravelAgencyRepository travelAgencyRepo;

    @Bean
    public Jdbi jdbi() {
        var url = env.getRequiredProperty("db.url");
        var user = env.getRequiredProperty("db.user");
        var password = env.getRequiredProperty("db.password");
        var jdbi = Jdbi.create(url, user, password);
        createTables(jdbi);
        replenishDb(jdbi);
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

        jdbi.useHandle(handle -> {
            handle.inTransaction(transactionHandle -> {
                transactionHandle.execute(countrySql);
                transactionHandle.execute(personSql);
                transactionHandle.execute(toursSql);
                transactionHandle.execute(reservationSql);
                transactionHandle.execute(componentsSql);
                return null;
            });
        });

    }

    private void replenishDb(Jdbi jdbi) {
    var dbReplenisher = new DbFiller();
        if (dbReplenisher.getAll(jdbi, Country.class, "countries").isEmpty()) {
            var countryConverter = new CountriesGsonConverter(gson());
            var deserializer = new CountriesDeserializer(countryConverter);
            var items = deserializer.deserialize("countries.json").countries();
            dbReplenisher.saveAll("countries", items, jdbi, Country.class);
        }
        if (dbReplenisher.getAll(jdbi, Tour.class, "tours").isEmpty()) {
            var toursConverter = new ToursGsonConverter(gson());
            var deserializer = new ToursDeserializer(toursConverter);
            var items = deserializer.deserialize("tours.json").tours();
            dbReplenisher.saveAll("tours", items, jdbi, Tour.class);
        }
        if (travelAgencyRepo.getAll().isEmpty()) {
            var travelAgencyConverter = new TravelAgenciesGsonConverter(gson());
            var deserializer = new TravelAgenciesDeserializer(travelAgencyConverter);
            travelAgencyRepo.saveAll(deserializer.deserialize("agencies.json").travelAgencies());
        }
    }


}
