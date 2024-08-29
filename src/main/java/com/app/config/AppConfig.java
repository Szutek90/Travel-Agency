package com.app.config;

import com.app.persistence.converter.impl.CountriesGsonConverter;
import com.app.persistence.converter.impl.ToursGsonConverter;
import com.app.persistence.converter.impl.TravelAgenciesGsonConverter;
import com.app.persistence.deserializer.custom.LocalDateDeserializer;
import com.app.persistence.deserializer.impl.CountriesDeserializer;
import com.app.persistence.deserializer.impl.ToursDeserializer;
import com.app.persistence.deserializer.impl.TravelAgenciesDeserializer;
import com.app.repository.CountryRepository;
import com.app.repository.TourRepository;
import com.app.repository.TravelAgencyRepository;
import com.app.repository.impl.CountryRepositoryImpl;
import com.app.repository.impl.TourRepositoryImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import java.time.LocalDate;

@Configuration
@ComponentScan("com.app")
@PropertySource({"classpath:application.properties"})
@RequiredArgsConstructor
public class AppConfig {
    private final Environment env;
    private final TravelAgencyRepository travelAgencyRepo;
    //TODO[3]
//    private final CountryRepository countryRepo;
//    private final TourRepository tourRepo;

    @Bean
    public Jdbi jdbi() {
        var url = env.getRequiredProperty("db.url");
        var user = env.getRequiredProperty("db.user");
        var password = env.getRequiredProperty("db.password");

        return Jdbi.create(url, user, password);
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .setPrettyPrinting().create();
    }

    @PostConstruct
    private void postConstruct() {
        createTables();
        replenishDb();
    }

    private void createTables() {
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
                CREATE TABLE IF NOT EXISTS reservations_components (
                    reservation_id INTEGER NOT NULL,
                    component VARCHAR(50) NOT NULL,
                    PRIMARY KEY (reservation_id, component),
                    FOREIGN KEY (reservation_id) REFERENCES reservations(id)
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

    private void replenishDb() {
        //TODO [2] Czy to jest poprawne, ze w tym miejscu jest to wstrzykiwane, a nie jak w Todo [3]?
        // jesli chce to wstrzyknac jak w miejscu Todo[3] to otrzymuje blad. Nie rozumiem dlaczego przy
        // TravelAgencyRepository nie otrzymuje bledu
        /*
        Exception in thread "main" org.springframework.beans.factory.UnsatisfiedDependencyException:
         Error creating bean with name 'appConfig': Unsatisfied dependency expressed through constructor parameter
         2: Error creating bean with name 'countryRepositoryImpl' defined in file
         [D:\KMPrograms\Java\GIT\TravelAgency\target\classes\com\app\repository\impl\CountryRepositoryImpl.class]:
          Unsatisfied dependency expressed through constructor parameter 0: Error creating bean with name
          'appConfig': Requested bean is currently in creation: Is there an unresolvable circular reference?

            Czy moze chodzic o to, ze w Country i Tour repository jest wykorzystywane jdbi, ktore ma Beana w tej klasie
            a travel agency repository nie wykorzystuje jdbi?
            Jesli dobrze podejrzewam to moze wystapic sytuacja, ze Bean jdbi nie zdarzy sie stworzyc aby wstrzyknac sie do
            dwoch repozytoriow
         */
        var countryRepo = new CountryRepositoryImpl(jdbi());
        var tourRepo = new TourRepositoryImpl(jdbi());

        if (countryRepo.findAll().isEmpty()) {
            var countryConverter = new CountriesGsonConverter(gson());
            var deserializer = new CountriesDeserializer(countryConverter);
            countryRepo.saveAll(deserializer.deserialize("countries.json").countries());
        }
        if (tourRepo.findAll().isEmpty()) {
            var toursConverter = new ToursGsonConverter(gson());
            var deserializer = new ToursDeserializer(toursConverter);
            tourRepo.saveAll(deserializer.deserialize("tours.json").tours());
        }
        if (travelAgencyRepo.getAll().isEmpty()) {
            var travelAgencyConverter = new TravelAgenciesGsonConverter(gson());
            var deserializer = new TravelAgenciesDeserializer(travelAgencyConverter);
            travelAgencyRepo.saveAll(deserializer.deserialize("agencies.json").travelAgencies());
        }
    }
}
