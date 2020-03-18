package by.bsu.service.impl;

import by.bsu.dao.OrderDAO;
import by.bsu.domain.Order;
import by.bsu.domain.Status;
import by.bsu.service.OrderService;
import by.bsu.validator.Validator;

import java.util.Optional;

public class OrderServiceimpl implements OrderService {

    Validator validator=new Validator();

    @Override
    public Order create(Order order) {
        validator.checkOrder(order);
        validator.checkDate(order.getDate());
        validator.checkStatus(order.getOrderStatus());
        return order;
    }

    @Override
    public Status checkStatus(Order order) {
        validator.checkStatus(order.getOrderStatus());
        return order.getOrderStatus();
    }

}
