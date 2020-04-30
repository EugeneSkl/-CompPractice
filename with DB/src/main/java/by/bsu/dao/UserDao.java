package by.bsu.dao;

import by.bsu.entity.UserEntity;

import java.util.Optional;

public interface UserDao extends CrudDao<UserEntity> {
    Optional<UserEntity> getByLogin(String login);
}