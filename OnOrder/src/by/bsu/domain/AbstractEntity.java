package by.bsu.domain;

import java.util.Objects;

public abstract class AbstractEntity<K> {

  private K id;

  public K getId() {
    return id;
  }

  public void setId(K id) {
    this.id = id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AbstractEntity that = (AbstractEntity) o;
    return id == that.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(getId());
  }

  @Override
  public String toString() {
    return "AbstractEntity{" + "id=" + id + '}';
  }
}
