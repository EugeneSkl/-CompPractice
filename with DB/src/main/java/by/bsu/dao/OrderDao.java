package by.bsu.dao;

import by.bsu.entity.OrderEntity;

import java.util.List;

public interface OrderDao extends CrudDao<OrderEntity> {
    boolean assignDish(long orderId, long dishId);

    boolean reAssignDish(long orderId, long dishId);

    List<OrderEntity> findByUser(Long userId);

    List<OrderEntity> findByStatus(String name);
}
