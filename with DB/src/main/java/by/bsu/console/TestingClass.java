package by.bsu.console;

import by.bsu.dao.implJDBC.DishDAOImplJD;
import by.bsu.dao.implJDBC.UserDAOImplJD;
import by.bsu.entity.UserEntity;

public class TestingClass {
    public static void main(String[] args) {
        DishDAOImplJD dishDAOImplJD=new DishDAOImplJD();
        UserEntity user=new UserEntity(7,"kkk","123");
        UserDAOImplJD userDAOImplJD=new UserDAOImplJD();
        userDAOImplJD.add(user);
        userDAOImplJD.getByLogin("kkk");
    }
}