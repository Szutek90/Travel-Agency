package com.app.db_managment.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.teeing;

public record RowData<T>(String column, T value) {
    public static String toInsertSql(List<RowData<?>> data) {
        return data
                .stream()
                .collect(teeing(
                        collectingAndThen(
                                Collectors.mapping(RowData::column, Collectors.toList()),
                                items -> String.join(", ", items)),
                                collectingAndThen(
                                        Collectors.mapping(row -> toSqlValue(row.value), Collectors.toList()),
                                        items -> String.join(", ", items)),
                        "(%s) values (%s)"::formatted
                        )
                );
    }

    public static String toUpdateSql(List<RowData<?>> data) {
        return data
                .stream()
                .map(row -> "%s=%s".formatted(
                        row.column,
                        toSqlValue(row.value)
                )).collect(Collectors.joining(", "));
    }

    private static <T> String toSqlValue(T value) {
        if (List.of(
                        String.class,
                        LocalDate.class,
                        LocalDateTime.class)
                .contains(value.getClass())) {
            return "'" + value + "'";
        }
        return String.valueOf(value);
    }
}
