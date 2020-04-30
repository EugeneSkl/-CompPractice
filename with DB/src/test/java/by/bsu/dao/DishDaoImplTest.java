package by.bsu.dao;

import by.bsu.TestConfiguration;
import by.bsu.dao.impl.DishDaoImpl;
import by.bsu.entity.DishEntity;
import by.bsu.exception.DishNotFoundException;
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
public class DishDaoImplTest {
    private EmbeddedDatabase embeddedDatabase;
    private DishDao dishDao;

    @Before
    public void init() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addScripts("/dish-table.sql", "/dish-data.sql",
                        "/order-table.sql", "/order-data.sql",
                        "/order_dish-table.sql", "/order_dish-data.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        dishDao = new DishDaoImpl(jdbcTemplate.getDataSource());
    }

    @After
    public void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    public void shouldFindAllDishes() {
        List<DishEntity> dishes = dishDao.findAll();
        Assert.assertEquals(4, dishes.size());
    }

    @Test
    public void shouldInsertDish() {
        List<DishEntity> beforeInsert = dishDao.findAll();
        DishEntity dishEntity = new DishEntity();
        dishEntity.setName("Pancake");
        dishEntity.setDescription("Some text");

        dishDao.add(dishEntity);
        List<DishEntity> afterInsert = dishDao.findAll();

        Assert.assertEquals(1, afterInsert.size() - beforeInsert.size());
    }

    @Test
    public void shouldUpdateDish() {
        long id = 1;
        DishEntity beforeUpdate = dishDao.getById(id);

        DishEntity updatingDish = new DishEntity(beforeUpdate.getId(),
                beforeUpdate.getName(), beforeUpdate.getDescription());
        updatingDish.setName("Changed");
        dishDao.update(updatingDish);

        DishEntity afterUpdate = dishDao.getById(id);
        Assert.assertNotEquals(beforeUpdate, afterUpdate);
    }

    @Test
    public void shouldRemoveDish() {
        long id = 2;

        List<DishEntity> beforeRemove = dishDao.findAll();
        dishDao.remove(id);
        List<DishEntity> afterRemove = dishDao.findAll();

        Assert.assertEquals(1, beforeRemove.size() - afterRemove.size());
    }

    @Test
    public void shouldGetById() {
        long id = 2;

        DishEntity expected = new DishEntity(id, "Sushi", "Sushi with fresh fish");
        DishEntity actual = dishDao.getById(id);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = DishNotFoundException.class)
    public void shouldThrowExcAfterGettingByInvalidId() {
        long invalidId = -111;
        dishDao.getById(invalidId);
    }

    @Test
    public void shouldFindDishByName() {
        String name = "Pasta";

        List<DishEntity> expected = Collections.singletonList(
                new DishEntity(3, "Pasta", "Italian pasta")
        );
        List<DishEntity> actual = dishDao.findByName(name);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void shouldFindOrderDishes() {
        List<DishEntity> found = dishDao.findDishesOfOrder(2L);

        Assert.assertEquals(3, found.size());
    }
}
