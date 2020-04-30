package by.bsu.service.impl;

import by.bsu.dao.DishDao;
import by.bsu.dto.DishDto;
import by.bsu.entity.DishEntity;
import by.bsu.service.DishService;
import by.bsu.util.DtoEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DishServiceImpl implements DishService {
    private DishDao dishDao;

    @Autowired
    public DishServiceImpl(DishDao dishDao) {
        this.dishDao = dishDao;
    }

    @Override
    public List<DishDto> findByName(String name) {
        List<DishEntity> entities = dishDao.findByName(name);
        return convertEachEntityToDto(entities);
    }

    @Override
    public List<DishDto> findDishesOfOrder(long orderId) {
        List<DishEntity> entities = dishDao.findDishesOfOrder(orderId);
        return convertEachEntityToDto(entities);
    }

    @Override
    public void save(DishDto dto) {
        DishEntity dishEntity = DtoEntityConverter.entityFromDto(dto);
        dishDao.add(dishEntity);
    }

    @Override
    public void delete(Long id) {
        dishDao.remove(id);
    }

    @Override
    public void update(DishDto dto) {
        DishEntity dishEntity = DtoEntityConverter.entityFromDto(dto);
        dishDao.update(dishEntity);
    }

    @Override
    public DishDto getById(Long id) {
        DishEntity entity = dishDao.getById(id);
        return DtoEntityConverter.dtoFromEntity(entity);
    }

    @Override
    public List<DishDto> findAll() {
        List<DishEntity> entities = dishDao.findAll();
        return convertEachEntityToDto(entities);
    }

    private List<DishDto> convertEachEntityToDto(List<DishEntity> entities) {
        return entities.stream()
                .map(DtoEntityConverter::dtoFromEntity)
                .collect(Collectors.toList());
    }
}
