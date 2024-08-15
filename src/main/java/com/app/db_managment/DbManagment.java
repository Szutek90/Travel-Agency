package com.app.db_managment;

import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;

import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DbManagment {
    private final Jdbi jdbi;

    public int createTable(String name, String pk, Map<String, String> columns) {
        var sql = """
                create table if not exists %s primary key (
                %s integer primary key autoincrement,
                %s
                )
                """.formatted(name, pk, createColumnsForNewTable(columns));
        return jdbi.withHandle(handle -> handle.execute(sql));
    }

    public int deleteTable(String name) {
        return jdbi.withHandle(handle -> handle.execute("delete from %s", name));
    }

    private String createColumnsForNewTable(Map<String, String> columns) {
        return columns.entrySet()
                .stream()
                .map(col -> col.getKey() + " " + col.getValue())
                .collect(Collectors.joining(", "));
    }
}
