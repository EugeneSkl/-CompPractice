package by.bsu.domain;

import java.util.Objects;
import java.util.logging.Logger;

public class Order extends AbstractEntity<Long>{


    private User user;
    private String date;
    private String notes;
    private String dish;

    public String getDish() {
        return dish;
    }

    public void setDish(String dish) {
        this.dish = dish;
    }

    protected Status orderStatus;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Status getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Status orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Order)) return false;
        if (!super.equals(o)) return false;
        Order order = (Order) o;
        return Objects.equals(getUser(), order.getUser()) &&
                Objects.equals(getDate(), order.getDate()) &&
                Objects.equals(getNotes(), order.getNotes()) &&
                Objects.equals(getDish(), order.getDish()) &&
                getOrderStatus() == order.getOrderStatus();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUser(), getDate(), getNotes(), getDish(), getOrderStatus());
    }

    @Override
    public String toString() {
        return "Order{" +
                "user=" + user +
                ", date='" + date + '\'' +
                ", notes='" + notes + '\'' +
                ", dish='" + dish + '\'' +
                ", orderStatus=" + orderStatus +
                '}';
    }
}
