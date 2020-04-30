package by.bsu.entity;

import javax.validation.constraints.NotNull;

public class AbstractEntity {
    @NotNull
    protected long id;

    public AbstractEntity() {
    }

    public AbstractEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
