package by.bsu.dao;

import by.bsu.entity.AbstractEntity;

import java.util.List;

public interface CrudDao<T extends AbstractEntity> {
    void add(T entity);

    void update(T entity);

    void remove(long id);

    T getById(long id);

    List<T> findAll();
}
