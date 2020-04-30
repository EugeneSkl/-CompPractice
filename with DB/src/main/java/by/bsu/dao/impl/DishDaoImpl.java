package by.bsu.dao.impl;

import by.bsu.dao.DishDao;
import by.bsu.entity.DishEntity;
import by.bsu.exception.DishNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.MessageFormat;
import java.util.List;

@Repository
public class DishDaoImpl implements DishDao {
    private static final String SELECT_ALL_QUERY = "select id, name, description from dish";
    private static final String SELECT_BY_FIELD_QUERY_TEMPLATE = "select id, name, description from dish where {0}";
    private static final String SELECT_BY_ORDER_QUERY = "select id, name, description from order_dish_relation rel inner " +
            "join dish on id=dish_id where order_id={0}";
    private static final String INSERT_QUERY = "insert into dish(name, description) values(?, ?)";
    private static final String UPDATE_QUERY = "update dish set name=?, description=? where id=?";
    private static final String DELETE_QUERY = "delete from dish where id=?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<DishEntity> rowMapper;

    @Autowired
    public DishDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.rowMapper = (rs, rowNum) -> {
            long id = rs.getLong("id");
            String name = rs.getString("name");
            String description = rs.getString("description");
            return new DishEntity(id, name, description);
        };
    }

    @Override
    public void add(DishEntity entity) {
        jdbcTemplate.update(INSERT_QUERY, entity.getName(), entity.getDescription());
    }

    @Override
    public void update(DishEntity entity) {
        jdbcTemplate.update(UPDATE_QUERY, entity.getName(),
                entity.getDescription(), entity.getId());
    }

    @Override
    public void remove(long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    @Override
    public DishEntity getById(long id) {
        String query = MessageFormat.format(SELECT_BY_FIELD_QUERY_TEMPLATE, "id='" + id + "'");
        List<DishEntity> foundDishes = jdbcTemplate.query(query, rowMapper);
        if (foundDishes.size() != 1) {
            throw new DishNotFoundException("Failed to get dish by id = " + id);
        }

        return foundDishes.get(0);
    }

    @Override
    public List<DishEntity> findByName(String name) {
        String query = MessageFormat.format(SELECT_BY_FIELD_QUERY_TEMPLATE, "name='" + name + "'");
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public List<DishEntity> findDishesOfOrder(long orderId) {
        String query = MessageFormat.format(SELECT_BY_ORDER_QUERY, "'" + orderId + "'");
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public List<DishEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, rowMapper);
    }
}
