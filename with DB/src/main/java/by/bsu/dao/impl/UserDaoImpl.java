package by.bsu.dao.impl;

import by.bsu.dao.UserDao;
import by.bsu.entity.UserEntity;
import by.bsu.exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Repository
public class UserDaoImpl implements UserDao {
    private static final String SELECT_ALL_QUERY = "select id, login, password from users";
    private static final String SELECT_BY_FIELD_QUERY_TEMPLATE = "select id, login, password from users where {0}";
    private static final String INSERT_QUERY = "insert into users(login, password) values(?, ?)";
    private static final String UPDATE_QUERY = "update users set login=?, password=? where id=?";
    private static final String DELETE_QUERY = "delete from users where id=?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<UserEntity> rowMapper;

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.rowMapper = (rs, rowNum) -> {
            long id = rs.getLong("id");
            String login = rs.getString("login");
            String password = rs.getString("password");
            return new UserEntity(id, login, password);
        };
    }

    @Override
    public void add(UserEntity entity) {
        jdbcTemplate.update(INSERT_QUERY, entity.getLogin(), entity.getPassword());
    }

    @Override
    public Optional<UserEntity> getByLogin(String login) {
        String query = MessageFormat.format(SELECT_BY_FIELD_QUERY_TEMPLATE, "login='" + login + "'");

        List<UserEntity> foundUsers = jdbcTemplate.query(query, rowMapper);

        return (foundUsers.size() == 1) ? Optional.ofNullable(foundUsers.get(0)) : Optional.empty();
    }

    @Override
    public UserEntity getById(long id) {
        String query = MessageFormat.format(SELECT_BY_FIELD_QUERY_TEMPLATE, "id='" + id + "'");

        List<UserEntity> foundUsers = jdbcTemplate.query(query, rowMapper);
        if (foundUsers.size() != 1) {
            throw new UserNotFoundException("Failed to find user by id = " + id);
        }

        return foundUsers.get(0);
    }

    @Override
    public List<UserEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, rowMapper);
    }

    @Override
    public void update(UserEntity entity) {
        jdbcTemplate.update(UPDATE_QUERY, entity.getLogin(),
                entity.getPassword(), entity.getId());

    }

    @Override
    public void remove(long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }
}
