package by.bsu.dto;

import by.bsu.entity.DishEntity;
import by.bsu.entity.StatusEntity;
import by.bsu.entity.UserEntity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;
import java.util.*;

public class OrderDto extends AbstractDto {
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

    private StatusEntity statusEntity;

    @NotNull
    private UserEntity userEntity;

    private List<DishEntity> dishes = new ArrayList<>();

    public OrderDto() {
    }

    public OrderDto(long id, Date date, String description, Long userId, Long statusId) {
        super(id);
        this.date = date;
        this.description = description;
        this.userId = userId;
        this.statusId = statusId;
    }

    public OrderDto(long id, Date date, String description, Long userId,
                    Long statusId, StatusEntity statusEntity, UserEntity userEntity) {
        super(id);
        this.date = date;
        this.description = description;
        this.userId = userId;
        this.statusId = statusId;
        this.statusEntity = statusEntity;
        this.userEntity = userEntity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public StatusEntity getStatusEntity() {
        return statusEntity;
    }

    public void setStatusEntity(StatusEntity statusEntity) {
        this.statusEntity = statusEntity;
    }

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public List<DishEntity> getDishes() {
        return new ArrayList<>(dishes);
    }

    public void setDishes(Collection<DishEntity> dishes) {
        this.dishes = new ArrayList<>(dishes);
    }

    public void addDish(DishEntity dishEntity) {
        dishes.add(dishEntity);
    }

    public void removeDish(DishEntity dishEntity) {
        dishes.remove(dishEntity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderDto orderDto = (OrderDto) o;
        return Objects.equals(date, orderDto.date) &&
                Objects.equals(description, orderDto.description) &&
                Objects.equals(userId, orderDto.userId) &&
                Objects.equals(statusId, orderDto.statusId) &&
                Objects.equals(statusEntity, orderDto.statusEntity) &&
                Objects.equals(userEntity, orderDto.userEntity) &&
                Objects.equals(dishes, orderDto.dishes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date, description, userId, statusId,
                statusEntity, userEntity, dishes);
    }

    @Override
    public String toString() {
        return "OrderDto{" +
                "date=" + date +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", statusId=" + statusId +
                ", statusEntity=" + statusEntity +
                ", userEntity=" + userEntity +
                ", dishes=" + dishes +
                '}';
    }
}
