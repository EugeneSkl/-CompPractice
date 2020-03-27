package by.bsu.console;

import by.bsu.domain.Order;
import by.bsu.domain.User;
import by.bsu.service.impl.OrderServiceimpl;

import java.util.Scanner;

public class OutputVariations {
    public void showDishes(User user){
        String[] dishMenu={"Pizza","Sushi","Pancakes"};
        System.out.println("Dish Menu");
        for (int i=1;i<dishMenu.length+1;i++){
            System.out.println(i+" "+dishMenu[i-1]);
        }
        System.out.println("Ready to make an order? (y/n)");
        Scanner scanner=new Scanner(System.in);
        if(scanner.next().contentEquals("y")){
            Order order =new Order(user);
            OrderServiceimpl orderServiceimpl=new OrderServiceimpl();
            orderServiceimpl.create(order);
        }
        else System.out.println("Well,see you later");

    }
}
