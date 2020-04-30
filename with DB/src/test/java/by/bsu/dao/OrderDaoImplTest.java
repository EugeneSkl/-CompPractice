package by.bsu.dao;

import by.bsu.TestConfiguration;
import by.bsu.dao.impl.OrderDaoImpl;
import by.bsu.entity.OrderEntity;
import by.bsu.exception.OrderNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class OrderDaoImplTest {
    private EmbeddedDatabase embeddedDatabase;
    private OrderDao orderDao;

    @Before
    public void init() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addScripts("/user-table.sql", "/user-data.sql",
                        "/status-table.sql", "/status-data.sql",
                        "/dish-table.sql", "/dish-data.sql",
                        "/order-table.sql", "/order-data.sql",
                        "/order_dish-table.sql", "/order_dish-data.sql"
                )
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        orderDao = new OrderDaoImpl(jdbcTemplate.getDataSource());
    }

    @After
    public void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    public void shouldFindAllOrders() {
        List<OrderEntity> orders = orderDao.findAll();
        Assert.assertEquals(3, orders.size());
    }

    @Test
    public void shouldInsertOrder() {
        List<OrderEntity> beforeInsert = orderDao.findAll();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setDate(new Date());
        orderEntity.setDescription("text");
        orderEntity.setUserId(1L);
        orderEntity.setStatusId(2L);

        orderDao.add(orderEntity);
        List<OrderEntity> afterInsert = orderDao.findAll();

        Assert.assertEquals(1, afterInsert.size() - beforeInsert.size());
    }

    @Test
    public void shouldUpdateOrder() {
        long id = 1;
        OrderEntity beforeUpdate = orderDao.getById(id);

        OrderEntity updatingOrder = new OrderEntity(beforeUpdate.getId(),
                beforeUpdate.getDate(), beforeUpdate.getDescription(),
                beforeUpdate.getUserId(), beforeUpdate.getStatusId());
        updatingOrder.setDescription("Changed description");
        orderDao.update(updatingOrder);

        OrderEntity afterUpdate = orderDao.getById(id);
        Assert.assertNotEquals(beforeUpdate, afterUpdate);
    }

    @Test
    public void shouldRemoveOrder() {
        long id = 1;

        List<OrderEntity> beforeRemove = orderDao.findAll();
        orderDao.remove(id);
        List<OrderEntity> afterRemove = orderDao.findAll();

        Assert.assertEquals(1, beforeRemove.size() - afterRemove.size());
    }

    @Test
    public void shouldGetById() {
        long id = 1;

        OrderEntity expected = new OrderEntity(id, new Date(),
                "Fast delivery", 1L, 1L);
        OrderEntity actual = orderDao.getById(id);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = OrderNotFoundException.class)
    public void shouldThrowExcAfterGettingByInvalidId() {
        long invalidId = -111;
        orderDao.getById(invalidId);
    }

    @Test
    public void shouldFindByUser() {
        long userId = 3;

        List<OrderEntity> expected = Collections.singletonList(
                new OrderEntity(3, new Date(),
                        "4 persons", 3L, 3L)
        );
        List<OrderEntity> actual = orderDao.findByUser(userId);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldFindByStatus() {
        String status = "Success";

        List<OrderEntity> expected = Collections.singletonList(
                new OrderEntity(3, new Date(),
                        "Fast delivery", 1L, 1L)
        );
        List<OrderEntity> actual = orderDao.findByStatus(status);

        Assert.assertEquals(expected, actual);
    }
}
