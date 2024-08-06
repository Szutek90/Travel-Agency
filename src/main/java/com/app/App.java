package com.app;

import com.app.config.DatabaseConfig;
import com.app.config.DatabaseConfigMapper;
import com.app.persistence.converter.impl.GsonConverter;
import com.app.persistence.deserializer.custom.LocalDateDeserializer;
import com.app.persistence.deserializer.impl.TravelAgenciesDeserializer;
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
        var converter = new GsonConverter(gson);
        var deserializer = new TravelAgenciesDeserializer(converter);
        var travelAgencies = deserializer.deserialize("agenciesWithTours.json");

        var createCountryTableSql = """
                create table if not exists country (
                id int primary key auto_increment,
                name varchar(255) not null)
                """;
        jdbi.useHandle(handle ->
                handle.execute(createCountryTableSql));
    }
}
