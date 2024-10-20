package com.app.db_managment;

import com.app.db_managment.model.RowData;
import lombok.RequiredArgsConstructor;
import org.jdbi.v3.core.Jdbi;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DbManagement {
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

    public void insert(String tableName, List<RowData<?>> data) {
        var insertSql = "insert into %s %s;".formatted(tableName, RowData.toInsertSql(data));
        jdbi.useHandle(handle -> handle.execute(insertSql));
    }

    public void update(String tableName, long id, List<RowData<?>> data) {
        var updateSql = "update %s set %s where id = %d".formatted(
                tableName,
                RowData.toUpdateSql(data),
                id
        );
        jdbi.useHandle(handle -> handle.execute(updateSql));
    }

    private String createColumnsForNewTable(Map<String, String> columns) {
        return columns.entrySet()
                .stream()
                .map(col -> col.getKey() + " " + col.getValue())
                .collect(Collectors.joining(", "));
    }
}
