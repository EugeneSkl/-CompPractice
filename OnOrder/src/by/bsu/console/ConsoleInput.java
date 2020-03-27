package by.bsu.console;

import by.bsu.dao.impl.UserDAOimplJSON;
import by.bsu.domain.User;
import by.bsu.exception.OrderException;
import by.bsu.validator.Validator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class ConsoleInput {

  static Logger logger = LogManager.getLogger();

  public void registrateUser() {

    Random rnd = new Random(System.currentTimeMillis());
    final Integer MIN = 100;
    final Integer MAX = 1000;

    System.out.println("Input login");
    Scanner scanner = new Scanner(System.in);
    try {
      if (scanner.hasNext()) {
        String login = scanner.next();
        UserDAOimplJSON userDAOimplJSON = new UserDAOimplJSON();
        User newUser = new User();
        Validator validator = new Validator();
        Integer generatedId = MIN + rnd.nextInt(MAX - MIN + 1);
        if (!validator.checkLogin(login)) {
          newUser.setName(login);
          System.out.println("Input Password");
          String password = scanner.next();
          newUser.setPassword(password);
          newUser.setId(validator.checkId(generatedId));
          userDAOimplJSON.update(newUser);
          new OutputMenu().outputMenu(newUser);
          logger.info("The new user created");
        } else {
          throw new OrderException("Login is already taken");
        }
      }
    } catch (IOException e) {
      logger.error("Problems with file");
    }
  }
}
