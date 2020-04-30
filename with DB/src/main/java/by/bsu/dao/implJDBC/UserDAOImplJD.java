package by.bsu.dao.implJDBC;

import by.bsu.config.JDBCPostgreConnection;
import by.bsu.dao.UserDao;
import by.bsu.entity.UserEntity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAOImplJD implements UserDao {
    private static JDBCPostgreConnection connection=new JDBCPostgreConnection();
    private static final Logger LOGGER = LogManager.getLogger();

    private static final String SELECT_ALL_QUERY = "select id, login, password from users";
    private static final String INSERT_QUERY = "insert into users(login, password) values(?, ?)";
    private static final String UPDATE_QUERY = "update users set login=?, password=? where id=?";
    private static final String DELETE_QUERY = "delete from users where id=?";



    @Override
    public void add(UserEntity entity) {
        try(PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(INSERT_QUERY);){
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2, entity.getPassword());
            LOGGER.info(preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e);
        }}

    @Override
    public Optional<UserEntity> getByLogin(String login) {
        try( Statement statement = connection.JDBCConnection().createStatement();) {
            String query="select id, login, password from users where login="+"'"+login+"'";
            LOGGER.info(query);
            ResultSet rs = statement.executeQuery(query);
            List<UserEntity> users=new ArrayList<>();
            while (rs.next()) {
                UserEntity userEntity=new UserEntity(rs.getLong("id"),login,rs.getString("password"));
                System.out.println(userEntity.toString());
                users.add(userEntity);
            }
            return (users.size() == 1) ? Optional.ofNullable(users.get(0)) : Optional.empty();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public UserEntity getById(long id) {
        try( Statement statement = connection.JDBCConnection().createStatement();) {
            String query="select id, login, password from users where id="+id;
            LOGGER.info(query);
            try (ResultSet rs = statement.executeQuery(query)) {
                if (rs.next()) {
                    UserEntity user = new UserEntity(id, rs.getString("login"), rs.getString("password"));
                    System.out.println(user.toString());
                    return user;
                }
            }
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return null;

    }

    @Override
    public List<UserEntity> findAll() {
        try(PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(SELECT_ALL_QUERY);) {
            List<UserEntity> foundUsers;
            try (ResultSet rs = preparedStatement.executeQuery()) {
                foundUsers = new ArrayList<>();
                while (rs.next()) {
                    UserEntity user = new UserEntity(rs.getLong("id"), rs.getString("login"), rs.getString("password"));
                    System.out.println(user.toString());
                    foundUsers.add(user);
                }
            }
            return foundUsers;
        } catch (SQLException e) {
            LOGGER.error(e);
        }
        return null;
    }

    @Override
    public void update(UserEntity entity) {
        try(PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(UPDATE_QUERY);){
            preparedStatement.setString(1, entity.getLogin());
            preparedStatement.setString(2,entity.getPassword());
            preparedStatement.setLong(3, entity.getId());
            LOGGER.info(preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void remove(long id) {
        try(PreparedStatement preparedStatement = connection.JDBCConnection().prepareStatement(DELETE_QUERY);) {
            preparedStatement.setLong(1, id);
            LOGGER.info(preparedStatement);
            preparedStatement.execute();
        } catch (SQLException e) {
            LOGGER.error(e);
        }
    }

}
