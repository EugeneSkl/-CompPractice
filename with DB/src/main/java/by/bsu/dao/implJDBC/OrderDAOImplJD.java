package by.bsu.dao.implJDBC;

import by.bsu.config.JDBCPostgreConnection;
import by.bsu.dao.OrderDao;
import by.bsu.entity.OrderEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImplJD implements OrderDao {
    private static JDBCPostgreConnection connection = new JDBCPostgreConnection();
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SELECT_ALL_QUERY = "select id, order_date, status_id, user_id, description from dish_order";
    private static final String INSERT_QUERY = "insert into dish_order(order_date, description, status_id, user_id) values(?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "update dish_order set order_date=?, description=?, status_id=?, user_id=? where id=?";
    private static final String DELETE_QUERY = "delete from dish_order where id=?";

    private static final String ASSIGN_DISH_QUERY = "select into order_dish_relation (order_id, dish_id) values(?, ?)";
    private static final String REASSIGN_DISH_QUERY = "delete from order_dish_relation where order_id=? and dish_id=?";


    @Override
    public void add(OrderEntity entity) {
        try (PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(INSERT_QUERY);) {
            preparedStatement.setDate(1, (Date) entity.getDate());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setLong(3, entity.getStatusId());
            preparedStatement.setLong(3, entity.getUserId());
            LOGGER.info(preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e);
        }

    }

    @Override
    public void update(OrderEntity entity) {
        try (PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(UPDATE_QUERY);
        ) {
            preparedStatement.setDate(1, (Date) entity.getDate());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setLong(3, entity.getUserId());
            preparedStatement.setLong(4, entity.getId());
            LOGGER.info(preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void remove(long id) {
        try (PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(DELETE_QUERY);
        ) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public boolean assignDish(long orderId, long dishId) {
        try (PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(ASSIGN_DISH_QUERY);
        ) {
            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, dishId);
            LOGGER.info(preparedStatement);
            return preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e);
            return false;
        }
    }

    @Override
    public boolean reAssignDish(long orderId, long dishId) {
        try (PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(REASSIGN_DISH_QUERY);
        ) {
            preparedStatement.setLong(1, orderId);
            preparedStatement.setLong(2, dishId);
            LOGGER.info(preparedStatement);
            return preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return false;
    }

    @Override
    public OrderEntity getById(long id) {
        try (Statement statement = connection.JDBCConnection().createStatement();
        ) {
            String query = "select id, order_date, status_id, user_id, description from dish_order where id=" + id;
            LOGGER.info(query);
            ResultSet rs = statement.executeQuery(query);
            while (rs.next()) {
                OrderEntity foundOrder = new OrderEntity(id, rs.getDate("date"), rs.getString("description"), rs.getLong("status_id"), rs.getLong("user_id"));
                System.out.println(foundOrder.toString());
                return foundOrder;
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return null;

    }

    @Override
    public List<OrderEntity> findByUser(Long userId) {
        try (Statement statement = connection.JDBCConnection().createStatement();
        ) {
            String query = "select id, order_date, status_id, user_id, description from dish_order where user_id" + "userID";
            LOGGER.info(query);
            List<OrderEntity> orders;
            try (ResultSet rs = statement.executeQuery(query)) {
                orders = new ArrayList<>();
                while (rs.next()) {
                    OrderEntity order = new OrderEntity(rs.getLong("id"), rs.getDate("date"), rs.getString("description"), rs.getLong("status_id"), userId);
                    System.out.println(order.toString());
                    orders.add(order);
                }
            }
            return orders;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return null;

    }

    @Override
    public List<OrderEntity> findByStatus(String name) {
        try (Statement statement = connection.JDBCConnection().createStatement();
        ) {
            String query = "select dish_order.id, order_date, status_id, user_id, description from dish_order inner join order_status on status_id=order_status.id where order_status.name=" + name;
            LOGGER.info(query);
            LOGGER.info(query);
            List<OrderEntity> orders;
            try (ResultSet rs = statement.executeQuery(query)) {
                orders = new ArrayList<>();
                while (rs.next()) {
                    OrderEntity order = new OrderEntity(rs.getLong("id"), rs.getDate("date"), rs.getString("description"), rs.getLong("status_id"), rs.getLong("user_id"));
                    System.out.println(order.toString());
                    orders.add(order);
                }
            }
            return orders;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return null;
    }


    @Override
    public List<OrderEntity> findAll() {
        try (PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(SELECT_ALL_QUERY);) {
            List<OrderEntity> foundOrders;
            try (ResultSet rs = preparedStatement.executeQuery()) {
                foundOrders = new ArrayList<>();
                while (rs.next()) {
                    OrderEntity foundOrder = new OrderEntity(rs.getLong("id"), rs.getDate("date"), rs.getString("description"), rs.getLong("userID"), rs.getLong("statusID"));
                    System.out.println(foundOrder.toString());
                    foundOrders.add(foundOrder);
                }
            }
            return foundOrders;
        } catch (SQLException e) {
            LOGGER.error(e);
        }

        return null;
    }
}
