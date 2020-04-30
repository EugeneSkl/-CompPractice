package by.bsu.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class DishDto extends AbstractDto {
    @NotNull
    @Size(min = 1, max = 30, message = "Dish name should not be greater than 30")
    private String name;

    @NotNull
    @Size(min = 1, max = 1000, message = "Dish description should not be greater than 1000")
    private String description;

    public DishDto() {
    }

    public DishDto(long id,  String name,  String description) {
        super(id);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        DishDto dishDto = (DishDto) o;
        return Objects.equals(name, dishDto.name) &&
                Objects.equals(description, dishDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }

    @Override
    public String toString() {
        return "DishDto{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
