package by.bsu.console;

import by.bsu.dao.impl.UserDAOimplJSON;
import by.bsu.exception.OrderException;
import by.bsu.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class SignIn {
  static Logger logger = LogManager.getLogger();

  public void userSignIn() {
    UserDAOimplJSON userDAOimplJSON = new UserDAOimplJSON();
    System.out.println("Enter login");
    Scanner scanner = new Scanner(System.in);
    if (scanner.hasNext()) {
      String login = scanner.next();
      try {
        Validator validator = new Validator();
        //Checking if the user already exists in the system
        if (validator.checkLogin(login)) {
          System.out.println("Enter password");
          String password = scanner.next();
          try {
              //checking password for a certain user
            if (validator.checkPassword(password)) {
                //Outputting menu for the user
              OutputMenu menu = new OutputMenu();
              menu.outputMenu(userDAOimplJSON.findUser(login));
            } else throw new OrderException();
          } catch (OrderException e) {
            logger.error("Invalid password");
          }
        } else throw new OrderException();
      } catch (OrderException e) {
        logger.error("Invalid login");
      }
    }
  }
}
