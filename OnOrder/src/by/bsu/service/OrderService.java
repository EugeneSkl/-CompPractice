package by.bsu.service;
import by.bsu.domain.Order;
import by.bsu.domain.Status;


public interface OrderService {
    Order create(Order order);

    Status checkStatus(Order order);


}
