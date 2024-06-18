package repo.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import db.DataSource;
import model.User;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import repo.UserRepository;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Properties;

class UserRepositoryImplTest {
    private static UserRepository repository;
    private static final String initSQLScript = "sql/initSQLScript.sql";
    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine");
    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() throws NoSuchFieldException, IllegalAccessException {
        postgresContainer.start();
        jdbcDatabaseDelegate = new JdbcDatabaseDelegate(postgresContainer, "");

        Properties props = getProperties();

        HikariDataSource ds = new HikariDataSource(new HikariConfig(props));
        DataSource dataSource = DataSource.getInstance();

        Field field = DataSource.class.getDeclaredField("ds");
        field.setAccessible(true);
        field.set(dataSource, ds);
    }

    @NotNull
    private static Properties getProperties() {
        Properties props = new Properties();
        props.setProperty("jdbcUrl", postgresContainer.getJdbcUrl());
        props.setProperty("dataSource.serverName", postgresContainer.getHost());
        props.setProperty("dataSource.portNumber", postgresContainer.getFirstMappedPort().toString());
        props.setProperty("dataSource.databaseName", postgresContainer.getDatabaseName());
        props.setProperty("dataSource.user", postgresContainer.getUsername());
        props.setProperty("dataSource.password", postgresContainer.getPassword());
        return props;
    }

    @AfterAll
    static void afterAll() {
        postgresContainer.stop();
    }

    @BeforeEach
    void prepareData() {
        repository = UserRepositoryImpl.getInstance();
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, initSQLScript);
    }
    @Test
    void getInstance() {
        Assertions.assertEquals(UserRepositoryImpl.getInstance().getClass(), UserRepositoryImpl.class);
    }

    @Test
    void save() {
        User userAdd = new User();
        userAdd.setName("Alex");
        userAdd.setAge(23);

        repository.save(userAdd);

        List<User> usersDB = repository.getAll();
        User userAddFromDB = usersDB.get(0);
        Assertions.assertEquals(userAddFromDB.getName(), userAdd.getName());
        Assertions.assertEquals(userAddFromDB.getAge(), userAdd.getAge());
    }

    @Test
    void update() {
        User userAdd = new User();
        userAdd.setName("Alex");
        userAdd.setAge(23);
        repository.save(userAdd);
        List<User> usersDB = repository.getAll();
        User userAddFromDB = usersDB.get(0);
        User userUpdate = new User();
        userUpdate.setUserId(userAddFromDB.getUserId());
        userUpdate.setName("Joe");
        userUpdate.setAge(23);

        repository.update(userUpdate);

        User userUpdateFromDB = repository.findById(userUpdate.getUserId());
        Assertions.assertEquals(userUpdateFromDB.getName(), userUpdate.getName());
        Assertions.assertEquals(userUpdateFromDB.getAge(), userUpdate.getAge());
    }

    @Test
    void delete() {
        User userAdd = new User();
        userAdd.setName("Alex");
        userAdd.setAge(23);
        repository.save(userAdd);
        List<User> usersDB = repository.getAll();
        User userAddFromDB = usersDB.get(0);
        User userDelete = new User();
        userDelete.setUserId(userAddFromDB.getUserId());

        repository.delete(userDelete);

        Assertions.assertEquals(0, repository.getAll().size());
    }

    @Test
    void getAll() {
        User userAdd = new User();
        userAdd.setName("Alex");
        userAdd.setAge(23);
        repository.save(userAdd);
        repository.save(userAdd);
        repository.save(userAdd);

        List<User> users = repository.getAll();

        Assertions.assertEquals(3, users.size());
    }

    @Test
    void findById() {
        User userAdd = new User();
        userAdd.setName("Alex");
        userAdd.setAge(22);
        repository.save(userAdd);
        userAdd.setName("Joe");
        userAdd.setAge(11);
        repository.save(userAdd);
        userAdd.setName("Alis");
        userAdd.setAge(33);
        repository.save(userAdd);

        User user = repository.findById(2);

        Assertions.assertEquals("Joe", user.getName());
        Assertions.assertEquals(11, user.getAge());
    }
}