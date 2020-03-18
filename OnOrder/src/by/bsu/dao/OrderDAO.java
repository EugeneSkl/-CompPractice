package by.bsu.dao;

import by.bsu.domain.*;

public interface OrderDAO extends GenericDAO<Order> {
    Status checkResult(Order order);
    Order findOrder(int id);
}
