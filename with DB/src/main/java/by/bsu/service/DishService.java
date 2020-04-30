package by.bsu.service;

import by.bsu.dto.DishDto;

import java.util.List;

public interface DishService extends CrudService<DishDto> {
    List<DishDto> findByName(String name);

    List<DishDto> findDishesOfOrder(long orderId);
}
