package com.app.config;

import com.google.common.base.CaseFormat;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class DbFiller {
    public <T> List<T> getAll(Jdbi jdbi, Class<T> type, String tableName) {
        var sql = "select * from %s".formatted(tableName);
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .mapToBean(type)
                .list());
    }

    public <T> void saveAll(String tableName, List<T> items, Jdbi jdbi, Class<T> type) {
        var sql = "insert into %s %s values %s".formatted(
                tableName,
                getColumnNames(type),
                items.stream()
                        .map(e -> getColumnValues(e, type))
                        .collect(Collectors.joining(", ")));
        var inserterRows = jdbi.withHandle(handle -> handle.execute(sql));
        if (inserterRows == 0) {
            throw new IllegalStateException("Rows not inserted");
        }
    }

    private <T> String getColumnNames(Class<T> type) {
        var cols = getDeclaredFieldsWithoutId(type)
                .map(field -> toLowerUnderscore(field.getName()))
                .collect(Collectors.joining(", "));
        return " ( %s ) ".formatted(cols);
    }

    private <T> String getColumnValues(Object item, Class<T> type) {
        var values = getDeclaredFieldsWithoutId(type)
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        if (field.get(item) == null) {
                            return "NULL";
                        }
                        if (List.of(String.class,
                                        Enum.class,
                                        LocalDate.class)
                                .contains(field.getType())) {
                            return "'%s'".formatted(field.get(item));
                        }
                        return field.get(item).toString();
                    } catch (IllegalAccessException e) {
                        throw new IllegalStateException(e);
                    }
                }).collect(Collectors.joining(", "));
        return "( %s ) ".formatted(values);
    }

    private <T> Stream<Field> getDeclaredFieldsWithoutId(Class<T> type) {
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> !field.getName().equalsIgnoreCase("id"));
    }

    private String toLowerUnderscore(String s) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, s);
    }
}
