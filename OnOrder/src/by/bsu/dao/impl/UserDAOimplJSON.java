package by.bsu.dao.impl;

import by.bsu.dao.UserDAO;
import by.bsu.domain.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserDAOimplJSON implements UserDAO {
  static Logger logger = LogManager.getLogger();
  ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public void create(User entity) {
    try {
      FileWriter fileWriter = new FileWriter("resources/users.json");
      List<User> users = new ArrayList<>();
      users.add(entity);
      objectMapper.writeValue(fileWriter, users);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public User read(int id) throws IOException {
    File file = new File("resources/users.json");
    List<User> users = readAll();
    for (User user : users) {
      if (user.getId() == id) {
        return user;
      }
    }
    return null;
  }

  @Override
  public User findUser(String name) {
    try {
      File file = new File("resources/users.json");
      List<User> users = readAll();
      for (User user : users) {
        if (user.getName().contentEquals(name)) {
          return user;
        }
      }
    } catch (IOException e) {
      logger.error("Cannot read file");
    }
    return null;
  }

  @Override
  public List<User> readAll() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    File file = new File("resources/users.json");
    List<User> user = objectMapper.readValue(file, new TypeReference<List<User>>() {});
    return user;
  }

  @Override
  public void update(User entity) throws IOException {
    try {
      List<User> users = readAll();
      File file = new File("resources/users.json");
      users.add(entity);
      objectMapper.writeValue(file, users);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("Stream error");
    }
  }

  @Override
  public void delete(int id) {
    try {
      objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
      File file = new File("resources/users.json");
      List<User> users = readAll();
      users.remove(read(id));
      objectMapper.writeValue(file, users);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("Stream error");
    }
  }
}
