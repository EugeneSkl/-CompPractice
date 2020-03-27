package by.bsu.dao;

import by.bsu.domain.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public interface GenericDAO<T extends AbstractEntity> {

  void create(T entity);

  T read(int id) throws IOException;

  List<T> readAll() throws IOException;

  void update(T entity) throws IOException;

  void delete(int id);
}
