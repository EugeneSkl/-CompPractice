package by.bsu.dao.implJDBC;

import by.bsu.config.JDBCPostgreConnection;
import by.bsu.dao.StatusDao;
import by.bsu.entity.StatusEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StatusDAOImplJD implements StatusDao {
    private static JDBCPostgreConnection connection = new JDBCPostgreConnection();
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SELECT_ALL_QUERY = "select id, name from order_status";
    private static final String INSERT_QUERY = "insert into order_status(name) values(?)";
    private static final String UPDATE_QUERY = "update order_status set name=? where id=?";
    private static final String DELETE_QUERY = "delete from order_status where id=?";

    @Override
    public void add(StatusEntity entity) {
        try (PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(INSERT_QUERY)) {
            preparedStatement.setString(1, entity.getName());
            LOGGER.info(preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void update(StatusEntity entity) {
        try (PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(UPDATE_QUERY)) {
            preparedStatement.setString(1, entity.getName());
            preparedStatement.setLong(2, entity.getId());
            LOGGER.info(preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void remove(long id) {
        try (PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(DELETE_QUERY)) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public StatusEntity getById(long id) {
        try (Statement statement = connection.JDBCConnection().createStatement();
        ) {
            String query = "select id, name from order_status  where id=" + id;
            LOGGER.info(query);
            try (ResultSet rs = statement.executeQuery(query)) {
                while (rs.next()) {
                    StatusEntity statusEntity = new StatusEntity(id, rs.getString("name"));
                    System.out.println(statusEntity.toString());
                    return statusEntity;
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return null;

    }

    @Override
    public List<StatusEntity> findByName(String name) {
        try (Statement statement = connection.JDBCConnection().createStatement()) {
            String query = "select id, name from order_status  where name=" + name;
            LOGGER.info(query);
            List<StatusEntity> foundStatus;
            try (ResultSet rs = statement.executeQuery(query)) {
                foundStatus = new ArrayList<>();
                while (rs.next()) {
                    StatusEntity statusEntity = new StatusEntity(rs.getLong("id"), name);
                    System.out.println(statusEntity.toString());
                    foundStatus.add(statusEntity);
                }
            }
            return foundStatus;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return Collections.emptyList();

    }

    @Override
    public List<StatusEntity> findStatusOfOrder(Long orderId) {
        try (Statement statement = connection.JDBCConnection().createStatement();
        ) {
            String query = "select order_status.id, name from order_status inner join dish_order on order_status.id=status_id where dish_order.id=" + orderId;
            LOGGER.info(query);
            List<StatusEntity> foundStatus;
            try (ResultSet rs = statement.executeQuery(query)) {
                foundStatus = new ArrayList<>();
                while (rs.next()) {
                    StatusEntity statusEntity = new StatusEntity(rs.getLong("id"), rs.getString("name"));
                    System.out.println(statusEntity.toString());
                    foundStatus.add(statusEntity);
                }
            }
            return foundStatus;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return Collections.emptyList();

    }

    @Override
    public List<StatusEntity> findAll() {
        try (PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(SELECT_ALL_QUERY);
        ) {
            List<StatusEntity> foundStatus;
            try (ResultSet rs = preparedStatement.executeQuery()) {
                foundStatus = new ArrayList<>();
                while (rs.next()) {
                    StatusEntity status = new StatusEntity(rs.getLong("id"), rs.getString("name"));
                    System.out.println(status.toString());
                    foundStatus.add(status);
                }
            }
            return foundStatus;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return Collections.emptyList();
    }
}

