package by.bsu.domain;

import org.apache.logging.log4j.Logger;

import java.util.Objects;

public class User extends AbstractEntity<Long>{


    private String name;
    private Integer orderNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return Objects.equals(getName(), user.getName());
    }
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getName());
    }

    @Override
    public String toString() {
        return "User{" +
                "User name='" + name + '\'' +
                ", Number of current orders=" + orderNumber +
                '}';
    }
}
