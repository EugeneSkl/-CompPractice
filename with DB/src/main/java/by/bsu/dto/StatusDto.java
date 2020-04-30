package by.bsu.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Objects;

public class StatusDto extends AbstractDto {
    @NotNull
    @Size(min = 1, max = 30, message = "Status name should not be greater than 30")
    private String name;

    public StatusDto() {
    }

    public StatusDto(long id, String name) {
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
        StatusDto statusDto = (StatusDto) o;
        return Objects.equals(name, statusDto.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return "StatusDto{" +
                "name='" + name + '\'' +
                '}';
    }
}
