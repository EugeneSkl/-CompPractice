package by.bsu.dao.impl;

import by.bsu.dao.OrderDao;
import by.bsu.entity.OrderEntity;
import by.bsu.exception.OrderNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.MessageFormat;
import java.util.Date;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {
    private static final String SELECT_ALL_QUERY = "select id, order_date, status_id, user_id, description from dish_order";
    private static final String SELECT_BY_STATUS_QUERY = "select dish_order.id, order_date, status_id, user_id, description from dish_order inner " +
            "join order_status on status_id=order_status.id where order_status.name={0}";
    private static final String SELECT_BY_FIELD_QUERY_TEMPLATE = "select id, order_date, status_id, user_id, description from dish_order where {0}";
    private static final String INSERT_QUERY = "insert into dish_order(order_date, description, status_id, user_id) values(?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "update dish_order set order_date=?, description=?, status_id=?, user_id=? where id=?";
    private static final String DELETE_QUERY = "delete from dish_order where id=?";

    private static final String ASSIGN_DISH_QUERY = "select into order_dish_relation (order_id, dish_id) values(?, ?)";
    private static final String REASSIGN_DISH_QUERY = "delete from order_dish_relation where order_id=? and dish_id=?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<OrderEntity> rowMapper;

    @Autowired
    public OrderDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        rowMapper = (rs, rowNum) -> {
            long id = rs.getLong("id");
            Date date = new Date(rs.getDate("order_date").getTime());
            String description = rs.getString("description");
            long statusId = rs.getLong("status_id");
            long userId = rs.getLong("user_id");
            return new OrderEntity(id, date, description, statusId, userId);
        };
    }

    @Override
    public void add(OrderEntity entity) {
        jdbcTemplate.update(INSERT_QUERY, entity.getDate(), entity.getDescription(),
                entity.getStatusId(), entity.getUserId());

    }

    @Override
    public void update(OrderEntity entity) {
        jdbcTemplate.update(UPDATE_QUERY, entity.getDate(), entity.getDescription(),
                entity.getStatusId(), entity.getUserId(), entity.getId());

    }

    @Override
    public void remove(long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    @Override
    public boolean assignDish(long orderId, long dishId) {
        int numOfUpdatedRows = jdbcTemplate.update(ASSIGN_DISH_QUERY, orderId, dishId);
        return numOfUpdatedRows > 0;
    }

    @Override
    public boolean reAssignDish(long orderId, long dishId) {
        int numOfDeletedRows = jdbcTemplate.update(REASSIGN_DISH_QUERY, orderId, dishId);
        return numOfDeletedRows > 0;
    }

    @Override
    public OrderEntity getById(long id) {
        String query = MessageFormat.format(SELECT_BY_FIELD_QUERY_TEMPLATE, "id='" + id + "'");

        List<OrderEntity> foundOrders = jdbcTemplate.query(query, rowMapper);
        if (foundOrders.size() != 1) {
            throw new OrderNotFoundException("Failed to find order by id = " + id);
        }

        return foundOrders.get(0);
    }

    @Override
    public List<OrderEntity> findByUser(Long userId) {
        String query =
                MessageFormat.format(SELECT_BY_FIELD_QUERY_TEMPLATE, "user_id='" + userId + "'");

        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public List<OrderEntity> findByStatus(String name) {
        String query = MessageFormat.format(SELECT_BY_STATUS_QUERY, "'" + name + "'");
        return jdbcTemplate.query(query, rowMapper);
    }


    @Override
    public List<OrderEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, rowMapper);
    }
}
