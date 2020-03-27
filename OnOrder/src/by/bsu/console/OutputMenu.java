package by.bsu.console;

import by.bsu.dao.impl.OrderDAOimplJSON;
import by.bsu.domain.Order;
import by.bsu.domain.User;
import by.bsu.service.impl.OrderServiceimpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class OutputMenu {
  static Logger logger = LogManager.getLogger();

  public void outputMenu(User user) {
    System.out.println("Press 1 - to List dishes");
    System.out.println("Press 2 - to make Order");
    Scanner scanner = new Scanner(System.in);
    if (scanner.hasNext()) {
      switch (scanner.next()) {
        case "1":
          ListDishes listDishes = new ListDishes();
          listDishes.showDishes(user);
          break;
        case "2":
          Order order = new Order(user);
          OrderServiceimpl orderServiceimpl = new OrderServiceimpl();
          orderServiceimpl.create(order);
          OrderDAOimplJSON orderDAOimplJSON = new OrderDAOimplJSON();
          orderDAOimplJSON.update(order);
          break;
        default:
          logger.error("Invalid value");
      }
    }
  }
}
