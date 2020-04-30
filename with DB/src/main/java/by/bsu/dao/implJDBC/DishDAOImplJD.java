package by.bsu.dao.implJDBC;

import by.bsu.config.JDBCPostgreConnection;
import by.bsu.dao.DishDao;
import by.bsu.entity.DishEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DishDAOImplJD implements DishDao {
    private static final Logger LOGGER = LogManager.getLogger();

    private static JDBCPostgreConnection connection = new JDBCPostgreConnection();
    private static final String SELECT_ALL_QUERY = "select id, name, description from dish";
    private static final String INSERT_QUERY = "insert into dish(name, description) values(?, ?)";
    private static final String UPDATE_QUERY = "update dish set name=?, description=? where id=?";
    private static final String DELETE_QUERY = "delete from dish where id=?";

    @Override
    public List<DishEntity> findByName(String name) {
        try (Statement statement = connection.JDBCConnection().createStatement()) {
            String query = "select id, name, description from dish where name=" + "name";
            LOGGER.info(query);
            List<DishEntity> foundDishes;
            try (ResultSet rs = statement.executeQuery(query)) {
                foundDishes = new ArrayList<>();
                while (rs.next()) {
                    DishEntity foundDish = new DishEntity(rs.getLong("id"), name, rs.getString("description"));
                    System.out.println(foundDish.toString() + " " + foundDish.getId());
                    foundDishes.add(foundDish);
                }
            }
            return foundDishes;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return Collections.emptyList();
    }

    @Override
    public List<DishEntity> findDishesOfOrder(long orderId) {
        try (Statement statement = connection.JDBCConnection().createStatement();
        ) {
            String query = "select id, name, description from order_dish_relation rel inner join dish on id=dish_id where order_id=" + orderId;
            LOGGER.info(query);
            List<DishEntity> foundDishes;
            try (ResultSet rs = statement.executeQuery(query)) {
                foundDishes = new ArrayList<DishEntity>();
                while (rs.next()) {
                    DishEntity foundDish = new DishEntity(rs.getLong("id"), rs.getString("name"), rs.getString("description"));
                    System.out.println(foundDish.toString());
                    foundDishes.add(foundDish);
                }
            }
            return foundDishes;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return Collections.emptyList();
    }

    @Override
    public void add(DishEntity entity) {
        try (PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(INSERT_QUERY);
        ) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            LOGGER.info(preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void update(DishEntity entity) {
        try (PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(UPDATE_QUERY);
        ) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setString(2, entity.getDescription());
            preparedStatement.setLong(3, entity.getId());
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
            LOGGER.info(preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e);

        }
    }

    @Override
    public DishEntity getById(long id) {
        try (Statement statement = connection.JDBCConnection().createStatement();
        ) {
            String query = "select id, name, description from dish where id=" + id;
            LOGGER.info(query);
            try (ResultSet rs = statement.executeQuery(query);) {
                if (rs.next()) {
                    DishEntity foundDish = new DishEntity(id, rs.getString("name"), rs.getString("description"));
                    System.out.println(foundDish.toString() + " " + foundDish.getId());
                    return foundDish;
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public List<DishEntity> findAll() {
        try (PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(SELECT_ALL_QUERY);
        ) {
            List<DishEntity> foundDishes;
            try (ResultSet rs = preparedStatement.executeQuery()) {
                foundDishes = new ArrayList<>();
                while (rs.next()) {
                    DishEntity foundDish = new DishEntity(rs.getLong("id"), rs.getString("name"), rs.getString("description"));
                    System.out.println(foundDish.toString() + foundDish.getId());
                    foundDishes.add(foundDish);
                }
            }
            return foundDishes;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return Collections.emptyList();
    }
}
