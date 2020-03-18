package by.bsu.validator;

import by.bsu.domain.Order;
import by.bsu.domain.Status;
import by.bsu.exception.OrderException;
public class Validator {
    public void checkOrder(Order order){
        if(order==null){
            throw new OrderException("Order is invalid ");
        }
    }

    public void checkDate(String date){
        if (date == null || date.isEmpty()) {
            throw new OrderException("Date is required");
        }
    }
    public void checkStatus(Status status){
        if (status == null ) {
            throw new OrderException("Status is invalid");
        }
    }
}
