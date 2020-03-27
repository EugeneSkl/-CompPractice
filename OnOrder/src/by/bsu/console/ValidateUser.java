package by.bsu.console;

import by.bsu.domain.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.omg.CORBA.DynAnyPackage.Invalid;

import java.util.InputMismatchException;
import java.util.Scanner;

public class ValidateUser {
    static Logger logger= LogManager.getLogger();
    public void validateUser(){
        User user1=new User("polina@gmail.com","123");
        User user2=new User("masha@gmail.com","qwerty");
        System.out.println("InputUser login");
        Scanner scanner = new Scanner(System.in);
        if(scanner.hasNext()){
            User user = new User();
            String login=scanner.next();
            try{
            if (login.contentEquals(user1.getName())||login.contentEquals(user2.getName())){
                System.out.println("InputUser password");
                String password=scanner.next();
                try{
                if (password.contentEquals(user1.getPassword())||password.contentEquals(user2.getPassword())){
                    OutputMenu menu=new OutputMenu();
                    menu.outputMenu(user1);
                }
                else throw new InputMismatchException();
                }  catch(InputMismatchException e){
                    logger.error("Invalid password");
                    }
                }
            else throw new InputMismatchException();
            }catch(InputMismatchException e){
                logger.error("Invalid login");
            }
        }
    }
}
