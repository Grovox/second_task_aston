package repo.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import db.DataSource;
import model.*;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import repo.*;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

class AuthorRepositoryImplTest {
    private static AuthorRepository repository;
    private static BookRepository bookRepository;
    private static AuthorBookRepository authorBookRepository;
    private static final String initSQLScript = "sql/initSQLScript.sql";
    @Container
    private static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine");
    private static JdbcDatabaseDelegate jdbcDatabaseDelegate;

    @BeforeAll
    static void beforeAll() throws NoSuchFieldException, IllegalAccessException {
        postgresContainer.start();
        jdbcDatabaseDelegate = new JdbcDatabaseDelegate(postgresContainer, "");

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

    @AfterAll
    static void afterAll() {
        postgresContainer.stop();
    }

    @BeforeEach
    void prepareData() {
        repository = AuthorRepositoryImpl.getInstance();
        bookRepository = BookRepositoryImpl.getInstance();
        authorBookRepository = AuthorBookRepositoryImpl.getInstance();
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, initSQLScript);
    }
    @Test
    void getInstance() {
        Assertions.assertEquals(AuthorRepositoryImpl.getInstance().getClass(), AuthorRepositoryImpl.class);
    }

    @Test
    void save() {
        Author authorAdd = new Author();
        authorAdd.setName("Joe");

        repository.save(authorAdd);

        List<Author> authorsDB = repository.getAll();
        Author authorsAddFromDB = authorsDB.get(0);
        Assertions.assertEquals(authorsAddFromDB.getName(), authorAdd.getName());
    }

    @Test
    void update() {
        Author authorAdd = new Author();
        authorAdd.setName("Joe");
        repository.save(authorAdd);
        List<Author> authorsDB = repository.getAll();
        Author authorsAddFromDB = authorsDB.get(0);
        Author authorUpdate = new Author();
        authorUpdate.setAuthorId(authorsAddFromDB.getAuthorId());
        authorUpdate.setName("Alis");

        repository.update(authorUpdate);

        Author authorUpdateFromDB = repository.findById(1);
        Assertions.assertEquals(authorUpdateFromDB.getName(), authorUpdate.getName());
    }

    @Test
    void delete() {
        Author authorAdd = new Author();
        authorAdd.setName("Joe");
        repository.save(authorAdd);
        List<Author> authorsDB = repository.getAll();
        Author authorsAddFromDB = authorsDB.get(0);
        Author authorDelete = new Author();
        authorDelete.setAuthorId(authorsAddFromDB.getAuthorId());

        repository.delete(authorDelete);

        Assertions.assertEquals(repository.getAll().size(), 0);
    }

    @Test
    void getAll() {
        Author authorAdd = new Author();
        authorAdd.setName("Joe");
        repository.save(authorAdd);
        repository.save(authorAdd);
        repository.save(authorAdd);

        List<Author> authors = repository.getAll();

        Assertions.assertEquals(authors.size(), 3);
    }

    @Test
    void findById() {
        Author authorAdd = new Author();
        authorAdd.setName("Joe");
        repository.save(authorAdd);
        authorAdd.setName("Alex");
        repository.save(authorAdd);
        authorAdd.setName("Alis");
        repository.save(authorAdd);

        Author author = repository.findById(2);

        Assertions.assertEquals(author.getName(), "Alex");
    }

    @Test
    void deleteAuthorByIdIfNotHaveBooks() {
        Book book = new Book();
        bookRepository.save(book);
        Author authorAdd = new Author();
        repository.save(authorAdd);
        repository.save(authorAdd);
        repository.save(authorAdd);
        repository.save(authorAdd);
        repository.save(authorAdd);
        AuthorBook authorBook1 = new AuthorBook();
        authorBook1.setBookId(1);
        authorBook1.setAuthorId(1);
        AuthorBook authorBook2 = new AuthorBook();
        authorBook2.setBookId(1);
        authorBook2.setAuthorId(2);
        List<AuthorBook> authorBookList = new ArrayList<>();
        authorBookList.add(authorBook1);
        authorBookList.add(authorBook2);
        authorBookRepository.saveAll(authorBookList);
        List<Integer> authorsId = new ArrayList<>();
        authorsId.add(1);
        authorsId.add(2);
        authorsId.add(3);
        authorsId.add(4);
        authorsId.add(5);

        repository.deleteAuthorByIdIfNotHaveBooks(authorsId);

        Assertions.assertEquals(repository.getAll().size(), 2);
    }
}