package by.bsu.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Objects;

public class OrderEntity extends AbstractEntity {
    @NotNull
    @PastOrPresent
    private Date date;

    @NotNull
    @Size(min = 1, max = 1500, message = "Order description should not be greater than 1500")
    private String description;

    @NotNull
    private Long userId;

    @NotNull
    private Long statusId;

    public OrderEntity() {
    }

    public OrderEntity(long id, Date date, String description, Long userId, Long statusId) {
        super(id);
        this.date = date;
        this.description = description;
        this.userId = userId;
        this.statusId = statusId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderEntity that = (OrderEntity) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(statusId, that.statusId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, userId, statusId);
    }

    @Override
    public String toString() {
        return "OrderEntity{" +
                "date=" + date +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", orderId=" + statusId +
                ", id=" + id +
                '}';
    }
}
