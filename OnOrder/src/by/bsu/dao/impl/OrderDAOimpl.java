package by.bsu.dao.impl;

import by.bsu.dao.OrderDAO;
import by.bsu.domain.*;

import java.util.List;

public class OrderDAOimpl implements OrderDAO {

    @Override
    public Status checkResult(Order order) {
        return order.getOrderStatus();
    }

    @Override
    public Order findOrder(int id) {
        return null;
    }

    @Override
    public Order create(Order entity) {
        return null;
    }

    @Override
    public Order read(long id) {
        return null;
    }

    @Override
    public List<Order> readAll() {
        return null;
    }

    @Override
    public void update(Order entity) {

    }

    @Override
    public void delete(Order entity) {

    }
}
