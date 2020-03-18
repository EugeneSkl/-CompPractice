package by.bsu.console;

import java.util.Scanner;
import by.bsu.domain.Order;
import by.bsu.service.OrderService;
import by.bsu.service.impl.OrderServiceimpl;


public class ConsoleInput {


    public void Input() {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Please, type your order ");
        Order order = new Order();
        if (scanner.hasNext()) {
            order.setNotes(scanner.next());
        }
        System.out.println(order.toString());
    }
}

