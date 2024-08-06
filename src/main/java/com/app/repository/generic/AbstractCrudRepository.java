package com.app.repository.generic;

import com.google.common.base.CaseFormat;
import org.atteo.evo.inflector.English;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractCrudRepository<T, ID> implements CrudRepository<T, ID> {
    private final Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    @Override
    public T save(T item) {
        var sql = "insert into %s %s values %s"
                .formatted(tableName(), getColumnNames(), getColumnValues(item));
        System.out.println(sql);
        return null;
    }

    @Override
    public List<T> saveAll(List<T> items) {
        return List.of();
    }

    @Override
    public T update(T item, ID id) {
        return null;
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.empty();
    }

    @Override
    public List<T> findLast(int n) {
        return List.of();
    }

    @Override
    public List<T> findAll() {
        return List.of();
    }

    @Override
    public List<T> deleteAllyByIds(List<ID> ids) {
        return List.of();
    }

    @Override
    public List<T> deleteAll() {
        return List.of();
    }

    @Override
    public T deleteById(ID id) {
        return null;
    }

    private String toLowerCamelCase(String s) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, s);
    }

    private String tableName() {
        return English.plural(toLowerCamelCase(type.getSimpleName()));
    }

    private String getColumnNames() {
        var cols = getDeclaredFieldsWithoutId()
                .map(field -> toLowerCamelCase(field.getName()))
                .collect(Collectors.joining(", "));
        return " ( %s ) ".formatted(cols);
    }

    private String getColumnValues(T item) {
        var values = getDeclaredFieldsWithoutId()
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

    private Stream<Field> getDeclaredFieldsWithoutId() {
        return Arrays.stream(type.getDeclaredFields())
                .filter(field -> !field.getName().equalsIgnoreCase("id"));
    }
}
