package by.bsu.dao;
import by.bsu.domain.*;

import java.util.List;

public interface GenericDAO<T extends AbstractEntity> {

    T create(T entity);

    T read(long id);

    List<T> readAll();

    void update(T entity);

    void delete(T entity);

}
