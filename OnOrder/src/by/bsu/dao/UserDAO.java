package by.bsu.dao;

import by.bsu.domain.User;

public interface UserDAO extends GenericDAO<User> {
  User findUser(String name);
}
