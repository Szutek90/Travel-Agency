package com.app.config;

import java.util.function.Function;

public interface DatabaseConfigMapper {
    Function<DatabaseConfig, String> getUrl = DatabaseConfig -> DatabaseConfig.url;
    Function<DatabaseConfig, String> getUsername = DatabaseConfig -> DatabaseConfig.username;
    Function<DatabaseConfig, String> getPassword = DatabaseConfig -> DatabaseConfig.password;
}
