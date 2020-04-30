package by.bsu.dao.impl;

import by.bsu.dao.StatusDao;
import by.bsu.entity.StatusEntity;
import by.bsu.exception.StatusNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.text.MessageFormat;
import java.util.List;

@Repository
public class StatusDaoImpl implements StatusDao {
    private static final String SELECT_ALL_QUERY = "select id, name from order_status";
    private static final String SELECT_BY_FIELD_QUERY_TEMPLATE = "select id, name from order_status where {0}";
    private static final String SELECT_BY_ORDER_QUERY = "select order_status.id, name from order_status inner join " +
            "dish_order on order_status.id=status_id where dish_order.id={0}";
    private static final String INSERT_QUERY = "insert into order_status(name) values(?)";
    private static final String UPDATE_QUERY = "update order_status set name=? where id=?";
    private static final String DELETE_QUERY = "delete from order_status where id=?";

    private JdbcTemplate jdbcTemplate;
    private RowMapper<StatusEntity> rowMapper;

    @Autowired
    public StatusDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.rowMapper = (rs, rowNum) -> {
            long id = rs.getLong("id");
            String description = rs.getString("name");
            return new StatusEntity(id, description);
        };
    }

    @Override
    public void add(StatusEntity entity) {
        jdbcTemplate.update(INSERT_QUERY, entity.getName());
    }

    @Override
    public void update(StatusEntity entity) {
        jdbcTemplate.update(UPDATE_QUERY, entity.getName(), entity.getId());
    }

    @Override
    public void remove(long id) {
        jdbcTemplate.update(DELETE_QUERY, id);
    }

    @Override
    public StatusEntity getById(long id) {
        String query =
                MessageFormat.format(SELECT_BY_FIELD_QUERY_TEMPLATE, "id='" + id + "'");
        List<StatusEntity> foundStatuses = jdbcTemplate.query(query, rowMapper);

        if (foundStatuses.size() != 1) {
            throw new StatusNotFoundException("Failed to get dish by id = " + id);
        }

        return foundStatuses.get(0);
    }

    @Override
    public List<StatusEntity> findByName(String name) {
        String query = MessageFormat.format(SELECT_BY_FIELD_QUERY_TEMPLATE, "name='" + name + "'");
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public List<StatusEntity> findStatusOfOrder(Long orderId) {
        String query = MessageFormat.format(SELECT_BY_ORDER_QUERY, "'" + orderId + "'");
        return jdbcTemplate.query(query, rowMapper);
    }

    @Override
    public List<StatusEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL_QUERY, rowMapper);
    }
}
