package by.bsu.validator;

import by.bsu.dao.impl.UserDAOimplJSON;
import by.bsu.domain.Order;
import by.bsu.domain.Status;
import by.bsu.domain.User;
import by.bsu.exception.OrderException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Validator {
  static final int MAX = 1000;
  static final int MIN = 100;
  static Logger logger = LogManager.getLogger();
  UserDAOimplJSON userDAOimplJSON = new UserDAOimplJSON();

  public void checkOrder(Order order) {
    if (order == null) {
      throw new OrderException("Order is invalid ");
    }
  }

  public void checkDate(String date) {
    if (date == null || date.isEmpty()) {
      throw new OrderException("Date is required");
    }
  }

  public void checkStatus(Status status) {
    if (status == null) {
      throw new OrderException("Status is invalid");
    }
  }
  /** Method that is used for checking if login already exists in the system */
  public boolean checkLogin(String login) {
    try {
      List<User> users = userDAOimplJSON.readAll();
      for (User user : users) {
        if (user.getName().contentEquals(login)) return true;
      }
    } catch (IOException e) {
      logger.error("Cannot read from user.json");
    }
    return false;
  }
  /** Method that is used for checking if password already exists in the system */
  public boolean checkPassword(String password) {
    try {
      List<User> users = userDAOimplJSON.readAll();
      for (User user : users) {
        if (user.getPassword().contentEquals(password)) return true;
      }
      throw new OrderException("Incorrect password");
    } catch (IOException e) {
      logger.error("Cannot read from user.json");
    }
    return false;
  }
  /** Method that is used for checking if generated id is already in the system */
  public Integer checkId(Integer id) {
    Random rnd = new Random(System.currentTimeMillis());
    try {
      List<User> users = userDAOimplJSON.readAll();
      for (User user : users) {
        if (user.getId() == id) return id = MIN + rnd.nextInt(MAX - MIN + 1);
      }
    } catch (IOException e) {
      logger.error("Cannot read from user.json");
    }
    return id;
  }
}
