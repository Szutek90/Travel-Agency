package com.app.config;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class DatabaseConfig {
    final String url;
    final String username;
    final String password;

    public DatabaseConfig(String propertiesFilePath) {
        var datas = loadFromFile(propertiesFilePath);
        this.url = datas.get("URL");
        this.username = datas.get("USERNAME");
        this.password = datas.get("PASSWORD");
    }

    private Map<String, String> loadFromFile(String propertiesFilePath) {
        try (var lines = Files.lines(Paths.get(propertiesFilePath))) {
            var datas = new HashMap<String, String>();
            for (var line : lines.toList()) {
                var splittedLine = line.split("=");
                datas.put(splittedLine[0], splittedLine[1]);
            }
            return datas;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}
