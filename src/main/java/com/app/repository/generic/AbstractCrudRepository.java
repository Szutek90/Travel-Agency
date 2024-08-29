package com.app.repository.generic;

import com.google.common.base.CaseFormat;
import lombok.RequiredArgsConstructor;
import org.atteo.evo.inflector.English;
import org.jdbi.v3.core.Jdbi;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public abstract class AbstractCrudRepository<T, ID> implements CrudRepository<T, ID> {
    protected final Jdbi jdbi;
    protected final Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
            .getActualTypeArguments()[0];

    @Override
    public T save(T item) {
        var sql = "insert into %s %s values %s"
                .formatted(tableName(), getColumnNames(), getColumnValues(item));
        System.out.println(sql);
        var insertedRows = jdbi.withHandle(handle -> handle.execute(sql));
        if (insertedRows == 0) {
            throw new IllegalStateException("Row not inserted");
        }

        return findLast(1).getFirst();
    }

    @Override
    public List<T> saveAll(List<T> items) {
        var sql = "insert into %s %s values %s".formatted(
                tableName(),
                getColumnNames(),
                items.stream()
                        .map(this::getColumnValues)
                        .collect(Collectors.joining(", ")));
        System.out.println(sql);
        var inserterRows = jdbi.withHandle(handle -> handle.execute(sql));
        if (inserterRows == 0) {
            throw new IllegalStateException("Rows not inserted");
        }
        return findLast(inserterRows);
    }

    @Override
    public T update(T item, ID id) {
        var sql = "update %s set %s where id = :id".formatted(tableName(), columnNamesAndValues(item));
        var updatedRows = jdbi.withHandle(handle -> handle
                .createUpdate(sql)
                .bind("id", id)
                .execute());

        if (updatedRows == 0) {
            throw new IllegalStateException("Row not updated");
        }
        return findById(id).orElseThrow();
    }

    @Override
    public Optional<T> findById(ID id) {
        var sql = "select * from %s where id = :id".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("id", id)
                .mapToBean(type)
                .findFirst());

    }

    @Override
    public List<T> findLast(int n) {
        var sql = "select * from %s order by id desc limit :n".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bind("n", n)
                .mapToBean(type)
                .list());
    }

    @Override
    public List<T> findAll() {
        var sql = "select * from %s order by id desc".formatted(tableName());
        return jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .mapToBean(type)
                .list());
    }

    @Override
    public List<T> findAllById(List<ID> ids) {
        var sql = "select * from %s where id in (<ids>)".formatted(tableName());
        var foundItems = jdbi.withHandle(handle -> handle
                .createQuery(sql)
                .bindList("ids", ids)
                .mapToBean(type)
                .list()
        );

        if (foundItems.size() != ids.size()) {
            throw new IllegalStateException("Found " + foundItems.size() + " items but expected " + ids.size());
        }
        return foundItems;
    }

    @Override
    public List<T> deleteAllyByIds(List<ID> ids) {
        var itemsToDelete = findAllById(ids);
        var sql = "delete from %s where id in (<ids>)".formatted(tableName());
        jdbi.useHandle(handle -> handle
                .createUpdate(sql)
                .bindList("ids", ids)
                .execute());
        return itemsToDelete;
    }

    @Override
    public List<T> deleteAll() {
        var itemsToDelete = findAll();
        var sql = "delete from %S".formatted(tableName());
        jdbi.useHandle(handle -> handle.execute(sql));
        return itemsToDelete;
    }

    @Override
    public T deleteById(ID id) {
        var itemToDelete = findById(id).orElseThrow(() ->
                new IllegalArgumentException("Item with that id not found"));
        var sql = "delete from %s where id = :id".formatted(tableName());
        jdbi.useHandle(handle -> handle
                .createUpdate(sql)
                .bind("id", id)
                .execute());
        return itemToDelete;
    }

    private String toLowerUnderscore(String s) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, s);
    }

    protected String tableName() {
        return English.plural(toLowerUnderscore(type.getSimpleName()));
    }

    private String getColumnNames() {
        var cols = getDeclaredFieldsWithoutId()
                .map(field -> toLowerUnderscore(field.getName()))
                .collect(Collectors.joining(", "));
        return " ( %s ) ".formatted(cols);
    }

    protected String getColumnValues(T item) {
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

    private String columnNamesAndValues(T item) {
        return getDeclaredFieldsWithoutId()
                .map(field -> {
                    try {
                        field.setAccessible(true);
                        if (List.of(String.class,
                                        Enum.class,
                                        LocalDate.class)
                                .contains(field.getType())) {
                            return "%s = '%s'".formatted(toLowerUnderscore(field.getName()), field.get(item));
                        }
                        return toLowerUnderscore(field.getName()) + " = " + field.get(item).toString();
                    } catch (Exception e) {
                        throw new IllegalStateException(e);
                    }
                }).collect(Collectors.joining(", "));
    }
}
