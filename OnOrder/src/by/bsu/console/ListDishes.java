package by.bsu.console;

import by.bsu.dao.impl.OrderDAOimplJSON;
import by.bsu.domain.Order;
import by.bsu.domain.User;
import by.bsu.service.impl.OrderServiceimpl;

import java.util.Scanner;
/** Class which is used for listing dish Menu */
public class ListDishes {
  String[] dishMenu = {"Pizza", "Sushi", "Pancakes"};
  /** Method used for showing dish menu and making order */
  public void showDishes(User user) {
    listDishes();
    System.out.println("Ready to make an order? (y/n)");
    Scanner scanner = new Scanner(System.in);
    if (scanner.next().contentEquals("y")) {
      Order order = new Order(user);
      new OrderServiceimpl().create(order);
      new OrderDAOimplJSON().update(order);
    } else System.out.println("Well,see you later");
  }
  /** * Simple listing */
  public void listDishes() {
    System.out.println("Dish Menu");
    for (int i = 1; i < dishMenu.length + 1; i++) {
      System.out.println(i + " " + dishMenu[i - 1]);
    }
  }
  /**
   * Choosing a dish out of list of options
   *
   * @param order - order for which we choose dishes
   */
  public void chooseDish(Order order) {
    Scanner scanner = new Scanner(System.in);
    String stringOrder = scanner.next().trim();
    int orderNum = Integer.parseInt(stringOrder);
    order.setDish(dishMenu[orderNum - 1]);
  }
}
