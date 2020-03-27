package by.bsu.console;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class OutputStart {
  static Logger logger = LogManager.getLogger();

  public static void main(String[] args) {
    System.out.println("Press 1 - if a you are a new user");
    System.out.println("Press 2 - if you are already a user");
    Scanner scanner = new Scanner(System.in);
    if (scanner.hasNext()) {
      switch (scanner.next()) {
        case "1":
          ConsoleInput consoleInput = new ConsoleInput();
          consoleInput.registrateUser();
          break;
        case "2":
          SignIn signIn = new SignIn();
          signIn.userSignIn();
          break;
        default:
          logger.error("Invalid value");
      }
    }
  }
}
