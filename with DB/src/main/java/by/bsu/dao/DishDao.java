package by.bsu.dao;

import by.bsu.entity.DishEntity;

import java.util.List;

public interface DishDao extends CrudDao<DishEntity> {
    List<DishEntity> findByName(String name);

    List<DishEntity> findDishesOfOrder(long orderId);
}
