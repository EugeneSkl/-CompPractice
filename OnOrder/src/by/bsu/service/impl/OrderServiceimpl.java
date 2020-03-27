package by.bsu.service.impl;

import by.bsu.console.ListDishes;

import by.bsu.domain.Order;
import by.bsu.domain.Status;
import by.bsu.service.OrderService;
import by.bsu.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;
import java.util.Scanner;

public class OrderServiceimpl implements OrderService {
  static Logger logger = LogManager.getLogger();
  Random rnd = new Random(System.currentTimeMillis());
  Validator validator = new Validator();
  static final Integer MAX = 1000;
  static final Integer MIN = 100;

  @Override
  public Order create(Order order) {
    System.out.println("Input Order");
    //setting date
    System.out.println("Please, input date of your order");
    Scanner scanner = new Scanner(System.in);
    String date = scanner.next();
    order.setDate(date);
    //setting id
    Integer generatedId = MIN + rnd.nextInt(MAX - MIN + 1);
    order.setId(validator.checkId(generatedId));
    //changing status
    order.setOrderStatus(Status.IN_PROGRESS);
    //listing dishes and choosing from them
    System.out.println("param 1 - Dish Index");
    new ListDishes().listDishes();
    new ListDishes().chooseDish(order);
    System.out.println("Any notes?(y/n)");
    if (scanner.next().contentEquals("y")) {
      System.out.println("Write your notes");
      scanner.nextLine();
      order.setNotes(scanner.nextLine());
    } else System.out.println(":((");
    logger.info("Order created");
    System.out.println(order.toString());
    return order;
  }

  @Override
  public Status checkStatus(Order order) {
    validator.checkStatus(order.getOrderStatus());
    return order.getOrderStatus();
  }
}
