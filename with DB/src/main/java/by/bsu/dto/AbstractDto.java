package by.bsu.dto;

public class AbstractDto {
    protected long id;

    public AbstractDto() {
    }

    public AbstractDto(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
