package by.bsu.service;

import by.bsu.dto.OrderDto;

import java.util.List;

public interface OrderService extends CrudService<OrderDto> {
    boolean assignDish(long orderId, long dishId);

    boolean reAssignDish(long orderId, long dishId);

    List<OrderDto> findByUser(Long userId);

    List<OrderDto> findByStatus(String name);
}
