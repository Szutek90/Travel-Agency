package com.app.service.impl.extension;

import com.app.config.DatabaseConfig;
import com.app.config.DatabaseConfigMapper;
import com.app.persistence.deserializer.custom.LocalDateDeserializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.time.LocalDate;

public class DatabaseExtension implements BeforeAllCallback {
    public static Gson gson;
    public static Jdbi jdbi;

    @Override
    public void beforeAll(ExtensionContext extensionContext) throws Exception {
        var databaseConfig = new DatabaseConfig("databaseProperties.txt");
        var jdbi = Jdbi.create(DatabaseConfigMapper.getUrl.apply(databaseConfig),
                DatabaseConfigMapper.getUsername.apply(databaseConfig),
                DatabaseConfigMapper.getPassword.apply(databaseConfig));
        var gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateDeserializer())
                .create();
    }
}
