package by.bsu.service.impl;

import by.bsu.dao.OrderDao;
import by.bsu.dto.OrderDto;
import by.bsu.entity.OrderEntity;
import by.bsu.service.OrderService;
import by.bsu.util.DtoEntityConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao;

    @Autowired
    public OrderServiceImpl(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public boolean assignDish(long orderId, long dishId) {
        return orderDao.assignDish(orderId, dishId);
    }

    @Override
    public boolean reAssignDish(long orderId, long dishId) {
        return orderDao.reAssignDish(orderId, dishId);
    }

    @Override
    public List<OrderDto> findByUser(Long userId) {
        List<OrderEntity> entities = orderDao.findByUser(userId);
        return convertEachEntityToDto(entities);
    }

    @Override
    public List<OrderDto> findByStatus(String name) {
        List<OrderEntity> entities = orderDao.findByStatus(name);
        return convertEachEntityToDto(entities);
    }

    @Override
    public void save(OrderDto dto) {
        OrderEntity orderEntity = DtoEntityConverter.entityFromDto(dto);
        orderDao.add(orderEntity);
    }

    @Override
    public void delete(Long id) {
        orderDao.remove(id);
    }

    @Override
    public void update(OrderDto dto) {
        OrderEntity orderEntity = DtoEntityConverter.entityFromDto(dto);
        orderDao.update(orderEntity);
    }

    @Override
    public OrderDto getById(Long id) {
        OrderEntity entity = orderDao.getById(id);
        return DtoEntityConverter.dtoFromEntity(entity);
    }

    @Override
    public List<OrderDto> findAll() {
        List<OrderEntity> entities = orderDao.findAll();
        return convertEachEntityToDto(entities);
    }

    private List<OrderDto> convertEachEntityToDto(List<OrderEntity> entities) {
        return entities.stream()
                .map(DtoEntityConverter::dtoFromEntity)
                .collect(Collectors.toList());
    }
}
