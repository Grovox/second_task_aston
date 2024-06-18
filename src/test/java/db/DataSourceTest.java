package db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import repo.AuthorBookRepository;
import repo.AuthorRepository;
import repo.BookRepository;
import repo.impl.AuthorBookRepositoryImpl;
import repo.impl.AuthorRepositoryImpl;
import repo.impl.BookRepositoryImpl;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.jupiter.api.Assertions.*;

class DataSourceTest {
    private static DataSource dataSource;
    @Container
    private static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine");

    @BeforeAll
    static void beforeAll() throws NoSuchFieldException, IllegalAccessException {
        postgresContainer.start();

        Properties props = new Properties();
        props.setProperty("jdbcUrl", postgresContainer.getJdbcUrl());
        props.setProperty("dataSource.serverName", postgresContainer.getHost());
        props.setProperty("dataSource.portNumber", postgresContainer.getFirstMappedPort().toString());
        props.setProperty("dataSource.databaseName", postgresContainer.getDatabaseName());
        props.setProperty("dataSource.user", postgresContainer.getUsername());
        props.setProperty("dataSource.password", postgresContainer.getPassword());

        HikariDataSource ds = new HikariDataSource(new HikariConfig(props));
        DataSource dataSource = DataSource.getInstance();

        Field field = DataSource.class.getDeclaredField("ds");
        field.setAccessible(true);
        field.set(dataSource, ds);
    }
    @BeforeEach
    void prepareData() {
        dataSource = DataSource.getInstance();
    }
    @AfterAll
    static void afterAll() {
        postgresContainer.stop();
    }
    @Test
    void getInstance() {
        Assertions.assertEquals(DataSource.getInstance().getClass(), DataSource.class);
    }

    @Test
    void getConnection() throws SQLException {

        Connection connection = dataSource.getConnection();

        ResultSet resultSet = connection.prepareStatement("SELECT  1").executeQuery();
        resultSet.next();
        Assertions.assertEquals(resultSet.getInt(1), 1);
    }
}