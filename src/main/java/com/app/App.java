package com.app;

import com.app.config.DatabaseConfig;
import com.app.config.DatabaseConfigMapper;
import com.app.persistence.converter.impl.CountriesGsonConverter;
import com.app.persistence.converter.impl.TravelAgenciesGsonConverter;
import com.app.persistence.deserializer.custom.LocalDateDeserializer;
import com.app.persistence.deserializer.impl.CountriesDeserializer;
import com.app.persistence.deserializer.impl.TravelAgenciesDeserializer;
import com.app.repository.impl.TravelAgencyRepositoryImpl;
import com.google.gson.GsonBuilder;
import org.jdbi.v3.core.Jdbi;

import java.time.LocalDate;


public class App {
    public static void main(String[] args) {
        var databaseConfig = new DatabaseConfig("databaseProperties.txt");
        var jdbi = Jdbi.create(DatabaseConfigMapper.getUrl.apply(databaseConfig),
                DatabaseConfigMapper.getUsername.apply(databaseConfig),
                DatabaseConfigMapper.getPassword.apply(databaseConfig));
        var gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
        var travelAgenciesGsonConverter = new TravelAgenciesGsonConverter(gson);
        var countriesGsonConverter = new CountriesGsonConverter(gson);
        var countriesDeserializer = new CountriesDeserializer(countriesGsonConverter);
        var travelAgenciesDeserializer = new TravelAgenciesDeserializer(travelAgenciesGsonConverter);
        var travelAgencies = travelAgenciesDeserializer.deserialize("agencies.json");
        var countries = countriesDeserializer.deserialize("countries.json");
        var travelAgencyRepo = new TravelAgencyRepositoryImpl(jdbi);
        System.out.println(travelAgencyRepo.save(travelAgencies.travelAgencies()));

//        var createAgencyTableSql = """
//                create table if not exists agency (
//                id int primary key auto_increment,
//                name varchar(255) not null).
//                city varchar(255) not null,
//                phone_number varchar(255) not null,
//                """;
//        jdbi.useHandle(handle ->
//                handle.execute(createAgencyTableSql));
    }
}
