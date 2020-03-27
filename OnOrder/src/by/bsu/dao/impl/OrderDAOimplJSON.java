package by.bsu.dao.impl;

import by.bsu.dao.OrderDAO;
import by.bsu.domain.Order;
import by.bsu.domain.Status;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOimplJSON implements OrderDAO {

  static Logger logger = LogManager.getLogger();
  ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public Status checkResult(Order order) {
    return order.getOrderStatus();
  }
/**creating new list*/
  @Override
  public void create(Order entity) {
    try {
      objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
      FileWriter fileWriter = new FileWriter("resources/order.json");
      List<Order> orders = new ArrayList<>();
      orders.add(entity);
      objectMapper.writeValue(fileWriter, orders);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
/***find a certain orer from its id*/
  @Override
  public Order read(int id) throws IOException {
    File file = new File("resources/order.json");
    List<Order> orders = readAll();
    for (Order order : orders) {
      if (order.getId() == id) {
        return order;
      }
    }
    return null;
  }
/***Get array of object from file*/
  @Override
  public List<Order> readAll() throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    File file = new File("resources/order.json");
    List<Order> order = objectMapper.readValue(file, new TypeReference<List<Order>>() {});
    return order;
  }
  /** * Get Array of orders from file add a new order to the array then rewrite the file */
  @Override
  public void update(Order entity) {
    try {
        //configuring output
      objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
      List<Order> orders = readAll();
      File file = new File("resources/order.json");
      orders.add(entity);
      objectMapper.writeValue(file, orders);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("Stream error");
    }
  }
    /** * Get Array of orders from file and delete the order using its id to the array then rewrite the file */

  @Override
  public void delete(int id) {
    try {
      objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
      File file = new File("resources/order.json");
      List<Order> orders = readAll();
      orders.remove(read(id));
      objectMapper.writeValue(file, orders);
    } catch (IOException e) {
      e.printStackTrace();
      logger.error("Stream error");
    }
  }
}
