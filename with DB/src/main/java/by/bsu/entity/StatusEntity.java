package by.bsu.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class StatusEntity extends AbstractEntity {
    @NotNull
    @Size(min = 1, max = 30, message = "Status name should not be greater than 30")
    private String name;

    public StatusEntity() {
    }

    public StatusEntity(long id, String name) {
        super(id);
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StatusEntity that = (StatusEntity) o;
        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "StatusEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
