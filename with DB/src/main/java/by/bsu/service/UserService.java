package by.bsu.service;

import by.bsu.dto.UserDto;

import java.util.Optional;

public interface UserService extends CrudService<UserDto> {
    Optional<UserDto> getByLogin(String login);
}
