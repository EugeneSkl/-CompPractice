package by.bsu.domain;

import java.util.Objects;

public class User extends AbstractEntity<Integer> {

  private String name;
  private String password;

  public User() {}

  public User(String name, String password) {
    this.name = name;
    this.password = password;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
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
    if (!(o instanceof User)) return false;
    if (!super.equals(o)) return false;
    User user = (User) o;
    return Objects.equals(getName(), user.getName())
        && Objects.equals(getPassword(), user.getPassword());
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), getName(), getPassword());
  }

  @Override
  public String toString() {
    return "Name= " + name;
  }
}
