package by.bsu.console;

import by.bsu.config.RepositoryConfig;
import by.bsu.dao.OrderDao;
import by.bsu.dao.UserDao;
import by.bsu.dao.impl.OrderDaoImpl;
import by.bsu.dao.impl.UserDaoImpl;
import by.bsu.dto.OrderDto;
import by.bsu.dto.UserDto;
import by.bsu.service.OrderService;
import by.bsu.service.UserService;
import by.bsu.service.impl.OrderServiceImpl;
import by.bsu.service.impl.UserServiceImpl;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class DatabaseStart {
    private static final Logger LOGGER = LogManager.getLogger();
    private static UserService userService;
    private static OrderService orderService;

    public static void main(String[] args) {
        init();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\n\n-------------------------------------------");
            System.out.println("Press 0 to close app");
            System.out.println("Press 1 to register new user");
            System.out.println("Press 2 to log in");
            System.out.println("------------------------------------------------\n");
            switch (scanner.next()) {
                case "1": {
                    System.out.println("Enter login:");
                    String login = scanner.next();
                    if (isLoginTaken(login)) {
                        System.out.println("Login already taken");
                        continue;
                    }
                    System.out.println("Enter password:");
                    String password = scanner.next();
                    userService.save(new UserDto(1, login, password));
                    break;
                }
                case "2": {
                    System.out.println("Enter login:");
                    String login = scanner.next();
                    if (!isLoginTaken(login)) {
                        System.out.println("Login dont exist");
                        continue;
                    }
                    System.out.println("Enter password:");
                    String password = scanner.next();
                    UserDto foundUser = userService.getByLogin(login).get();
                    if (foundUser.getPassword().equals(password)) {
                        System.out.println("Logged in!");
                        showOrderMenu(scanner, foundUser.getId());
                    } else {
                        System.out.println("Wrong password!");
                    }
                    break;
                }
                default: {
                    LOGGER.error("Invalid value");
                    return;
                }
            }
        }
    }

    private static void showOrderMenu(Scanner scanner, long userId) {
        int choice;
        do {
            System.out.println("\n\n-------------------------------------------");
            System.out.println("Type 0 to logout");
            System.out.println("Type 1 to get all orders");
            System.out.println("Type 2 to create order");
            System.out.println("Type 3 to delete order");
            System.out.println("------------------------------------------------\n");
            choice = scanner.nextInt();
            switch (choice) {
                case 1: {
                    showUserOrders(userId);
                    break;
                }
                case 2: {
                    createNewOrder(scanner, userId);
                    break;
                }
                case 3: {
                    deleteOrder(scanner);
                    break;
                }
            }
        } while (choice != 0);
    }

    private static void showUserOrders(long userId) {
        List<OrderDto> userOrders = orderService.findByUser(userId);
        if (userOrders.isEmpty()) {
            System.out.println("You have no orders!");
        } else {
            System.out.println("Order list:");
            userOrders.forEach(System.out::println);
        }
    }

    private static void deleteOrder(Scanner scanner) {
        System.out.println("Enter oder id to delete");
        long idToDelete = scanner.nextLong();
        orderService.delete(idToDelete);
    }

    private static void createNewOrder(Scanner scanner, long userId) {
        System.out.println("Please, input date of your order:");
        String dateString = scanner.next();
        Date date = new Date(dateString);

        String description = "";
        System.out.println("Any notes?(y/n)");
        if (scanner.next().contentEquals("y")) {
            System.out.println("Write your notes");
            scanner.nextLine();
            description = scanner.nextLine();
        }

        OrderDto orderDto = new OrderDto(1, date, description, userId, 1L);
        orderService.save(orderDto);
    }

    private static void init() {
        RepositoryConfig repositoryConfig = new RepositoryConfig();
        DataSource dataSource = repositoryConfig.dataSource();

        UserDao userDAO = new UserDaoImpl(dataSource);
        OrderDao orderDAO = new OrderDaoImpl(dataSource);

        userService = new UserServiceImpl(userDAO);
        orderService = new OrderServiceImpl(orderDAO);
    }

    private static boolean isLoginTaken(String login) {
        return userService.getByLogin(login).isPresent();
    }
}
