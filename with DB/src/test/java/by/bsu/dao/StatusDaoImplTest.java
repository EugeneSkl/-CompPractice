package by.bsu.dao;

import by.bsu.TestConfiguration;
import by.bsu.dao.impl.StatusDaoImpl;
import by.bsu.entity.StatusEntity;
import by.bsu.exception.StatusNotFoundException;
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
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class StatusDaoImplTest {
    private EmbeddedDatabase embeddedDatabase;
    private StatusDao statusDao;

    @Before
    public void init() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addScripts("/status-table.sql", "/status-data.sql",
                        "/order-table.sql", "/order-data.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        statusDao = new StatusDaoImpl(jdbcTemplate.getDataSource());
    }

    @After
    public void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    public void shouldFindAllStatuses() {
        List<StatusEntity> found = statusDao.findAll();
        Assert.assertEquals(4, found.size());
    }

    @Test
    public void shouldInsertStatus() {
        List<StatusEntity> beforeInsert = statusDao.findAll();
        StatusEntity statusEntity = new StatusEntity();
        statusEntity.setName("New");

        statusDao.add(statusEntity);
        List<StatusEntity> afterInsert = statusDao.findAll();

        Assert.assertEquals(1, afterInsert.size() - beforeInsert.size());
    }

    @Test
    public void shouldUpdateStatus() {
        long id = 1;
        StatusEntity beforeUpdate = statusDao.getById(id);

        StatusEntity updatingStatus =
                new StatusEntity(beforeUpdate.getId(), beforeUpdate.getName());
        updatingStatus.setName("Updated");
        statusDao.update(updatingStatus);

        StatusEntity afterUpdate = statusDao.getById(id);
        Assert.assertNotEquals(beforeUpdate, afterUpdate);
    }

    @Test
    public void shouldRemoveStatus() {
        long id = 1;

        List<StatusEntity> beforeRemove = statusDao.findAll();
        statusDao.remove(id);
        List<StatusEntity> afterRemove = statusDao.findAll();

        Assert.assertEquals(1, beforeRemove.size() - afterRemove.size());
    }

    @Test
    public void shouldGetById() {
        long id = 1;

        StatusEntity expected = new StatusEntity(id, "Success");
        StatusEntity actual = statusDao.getById(id);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = StatusNotFoundException.class)
    public void shouldThrowExcAfterGettingByInvalidId() {
        long invalidId = -111;
        statusDao.getById(invalidId);
    }

    @Test
    public void shouldGetOrderStatus() {
        long id = 1;

        List<StatusEntity> expected = Collections.singletonList(
                new StatusEntity(1, "Success")
        );
        List<StatusEntity> actual = statusDao.findStatusOfOrder(id);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldFindStatusesByName() {
        String name = "Cancelled";

        List<StatusEntity> expected = Collections.singletonList(
                new StatusEntity(3, name)
        );
        List<StatusEntity> actual = statusDao.findByName(name);

        Assert.assertEquals(expected, actual);
    }
}