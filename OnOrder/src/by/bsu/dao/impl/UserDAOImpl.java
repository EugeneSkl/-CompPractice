package by.bsu.dao.impl;

import by.bsu.console.ConsoleInput;
import by.bsu.dao.OrderDAO;
import by.bsu.dao.UserDAO;
import by.bsu.domain.*;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class UserDAOImpl implements UserDAO {

  @Override
  public void create(User entity) {}

  @Override
  public User findUser(String name) {
    return null;
  }

  @Override
  public User read(int id) throws IOException {
    return null;
  }

  @Override
  public List<User> readAll() throws IOException {
    return null;
  }

  @Override
  public void update(User entity) throws IOException {}

  @Override
  public void delete(int id) {}
}
