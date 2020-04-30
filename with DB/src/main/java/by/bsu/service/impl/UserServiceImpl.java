package by.bsu.service.impl;

import by.bsu.dao.UserDao;
import by.bsu.dto.UserDto;
import by.bsu.entity.UserEntity;
import by.bsu.service.UserService;
import by.bsu.util.DtoEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Optional<UserDto> getByLogin(String login) {
        Optional<UserEntity> entity = userDao.getByLogin(login);
        return entity.map(DtoEntityConverter::dtoFromEntity);
    }

    @Override
    public void save(UserDto dto) {
        UserEntity userEntity = DtoEntityConverter.entityFromDto(dto);
        userDao.add(userEntity);
    }

    @Override
    public void delete(Long id) {
        userDao.remove(id);
    }

    @Override
    public void update(UserDto dto) {
        UserEntity userEntity = DtoEntityConverter.entityFromDto(dto);
        userDao.update(userEntity);
    }

    @Override
    public UserDto getById(Long id) {
        UserEntity userEntity = userDao.getById(id);
        return DtoEntityConverter.dtoFromEntity(userEntity);
    }

    @Override
    public List<UserDto> findAll() {
        List<UserEntity> entities = userDao.findAll();
        return convertEachEntityToDto(entities);
    }

    private List<UserDto> convertEachEntityToDto(List<UserEntity> entities) {
        return entities.stream()
                .map(DtoEntityConverter::dtoFromEntity)
                .collect(Collectors.toList());
    }
}
