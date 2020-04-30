package by.bsu.dao;

import by.bsu.TestConfiguration;
import by.bsu.dao.impl.UserDaoImpl;
import by.bsu.entity.UserEntity;
import by.bsu.exception.UserNotFoundException;
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

import java.util.List;
import java.util.Optional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfiguration.class)
public class UserDaoImplTest {
    private EmbeddedDatabase embeddedDatabase;
    private UserDao userDao;

    @Before
    public void init() {
        embeddedDatabase = new EmbeddedDatabaseBuilder()
                .addScripts("/user-table.sql", "/user-data.sql")
                .setType(EmbeddedDatabaseType.H2)
                .build();
        JdbcTemplate jdbcTemplate = new JdbcTemplate(embeddedDatabase);
        userDao = new UserDaoImpl(jdbcTemplate.getDataSource());
    }

    @After
    public void tearDown() {
        embeddedDatabase.shutdown();
    }

    @Test
    public void shouldFindAllUsers() {
        List<UserEntity> users = userDao.findAll();
        Assert.assertEquals(5, users.size());
    }

    @Test
    public void shouldInsertUser() {
        List<UserEntity> beforeInsert = userDao.findAll();
        UserEntity userEntity = new UserEntity();
        userEntity.setLogin("test");
        userEntity.setPassword("test");

        userDao.add(userEntity);
        List<UserEntity> afterInsert = userDao.findAll();
        Assert.assertEquals(1, afterInsert.size() - beforeInsert.size());
    }

    @Test
    public void shouldUpdateUser() {
        long id = 1;
        UserEntity beforeUpdate = userDao.getById(id);

        UserEntity updatingUser = new UserEntity(beforeUpdate.getId(),
                beforeUpdate.getLogin(), beforeUpdate.getPassword());
        updatingUser.setLogin("Changed");
        userDao.update(updatingUser);

        UserEntity afterUpdate = userDao.getById(id);
        Assert.assertNotEquals(beforeUpdate, afterUpdate);
    }

    @Test
    public void shouldRemoveUser() {
        long id = 1;

        List<UserEntity> beforeRemove = userDao.findAll();
        userDao.remove(id);
        List<UserEntity> afterRemove = userDao.findAll();

        Assert.assertEquals(1, beforeRemove.size() - afterRemove.size());
    }

    @Test
    public void shouldGetById() {
        long id = 2;

        UserEntity expected = new UserEntity(id, "Eugene", "King");
        UserEntity actual = userDao.getById(id);

        Assert.assertEquals(expected, actual);
    }

    @Test(expected = UserNotFoundException.class)
    public void shouldThrowExcAfterGettingByInvalidId() {
        long invalidId = -111;
        userDao.getById(invalidId);
    }

    @Test
    public void shouldGetUserByLogin() {
        String login = "Max";

        UserEntity expected = new UserEntity(2, "Max", "White");
        Optional<UserEntity> optional = userDao.getByLogin(login);

        Assert.assertTrue(optional.isPresent());
        Assert.assertEquals(expected, optional.get());
    }
}
