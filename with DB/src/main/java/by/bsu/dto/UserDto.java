package by.bsu.dto;

import by.bsu.entity.OrderEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDto extends AbstractDto {
    @NotNull
    @Size(min = 1, max = 30, message = "User login should not be greater than 30")
    private String login;

    @NotNull
    @Size(min = 1, max = 50, message = "User password should not be greater than 50")
    private String password;

    private List<OrderEntity> orders = new ArrayList<>();

    public UserDto() {
    }

    public UserDto(long id, String login, String password) {
        super(id);
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<OrderEntity> getOrders() {
        return new ArrayList<>(orders);
    }

    public void setOrders(Collection<OrderEntity> orders) {
        this.orders = new ArrayList<>(orders);
    }

    public void addOrder(OrderEntity orderEntity) {
        orders.add(orderEntity);
    }

    public void removeOrder(OrderEntity orderEntity) {
        orders.remove(orderEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto userDto = (UserDto) o;
        return Objects.equals(login, userDto.login) &&
                Objects.equals(password, userDto.password) &&
                Objects.equals(orders, userDto.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, password, orders);
    }

    @Override
    public String toString() {
        return "UserDto{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", orders=" + orders +
                '}';
    }
}
