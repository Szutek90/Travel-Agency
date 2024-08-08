package com.app.repository.generic;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {
    T save(T item);

    List<T> saveAll(List<T> items);

    T update(T item, ID id);

    Optional<T> findById(ID id);

    List<T> findLast(int n);

    List<T> findAll();

    List<T> findAllById(List<ID> ids);

    List<T> deleteAllyByIds(List<ID> ids);

    List<T> deleteAll();

    T deleteById(ID id);
}
