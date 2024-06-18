package repo.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import db.DataSource;
import dto.BookGet;
import model.Book;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import repo.BookRepository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

class BookRepositoryImplTest {
    private static BookRepository repository;
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
        repository = BookRepositoryImpl.getInstance();
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, initSQLScript);
    }
    @Test
    void getInstance() {
        Assertions.assertEquals(BookRepositoryImpl.getInstance().getClass(), BookRepositoryImpl.class);
    }

    @Test
    void save() {
        Book bookAdd = new Book();
        bookAdd.setTitle("Book");
        bookAdd.setPrice(123.12f);
        bookAdd.setAmount(50);

        repository.save(bookAdd);

        List<BookGet> booksDB = repository.getAll();
        BookGet bookAddFromDB = booksDB.get(0);
        Assertions.assertEquals(bookAddFromDB.getTitle(), bookAdd.getTitle());
        Assertions.assertEquals(bookAddFromDB.getPrice(), bookAdd.getPrice());
        Assertions.assertEquals(bookAddFromDB.getAmount(), bookAdd.getAmount());
    }

    @Test
    void update() {
        Book bookAdd = new Book();
        bookAdd.setTitle("Book");
        bookAdd.setPrice(123.12f);
        bookAdd.setAmount(50);
        repository.save(bookAdd);
        List<BookGet> booksDB = repository.getAll();
        BookGet bookAddFromDB = booksDB.get(0);
        Book bookUpdate = new Book();
        bookUpdate.setBookId(bookAddFromDB.getBookId());
        bookUpdate.setTitle("BookUpdate");
        bookUpdate.setPrice(333.3f);
        bookUpdate.setAmount(3);

        repository.update(bookUpdate);

        Book bookUpdateFromDB = repository.findById(bookUpdate.getBookId());
        Assertions.assertEquals(bookUpdateFromDB.getTitle(), bookUpdate.getTitle());
        Assertions.assertEquals(bookUpdateFromDB.getPrice(), bookUpdate.getPrice());
        Assertions.assertEquals(bookUpdateFromDB.getAmount(), bookUpdate.getAmount());
    }

    @Test
    void delete() {
        Book bookAdd = new Book();
        bookAdd.setTitle("Book");
        bookAdd.setPrice(123.12f);
        bookAdd.setAmount(50);
        repository.save(bookAdd);
        List<BookGet> booksDB = repository.getAll();
        BookGet bookAddFromDB = booksDB.get(0);
        Book bookDelete = new Book();
        bookDelete.setBookId(bookAddFromDB.getBookId());

        repository.delete(bookDelete);

        Assertions.assertEquals(repository.getAll().size(), 0);
    }

    @Test
    void getAll() {
        Book bookAdd = new Book();
        bookAdd.setTitle("Book");
        bookAdd.setPrice(123.12f);
        bookAdd.setAmount(50);
        repository.save(bookAdd);
        repository.save(bookAdd);
        repository.save(bookAdd);

        List<BookGet> bookGets = repository.getAll();

        Assertions.assertEquals(bookGets.size(), 3);
    }

    @Test
    void findById() {
        Book bookAdd = new Book();
        bookAdd.setTitle("Book1");
        bookAdd.setPrice(111.11f);
        bookAdd.setAmount(11);
        repository.save(bookAdd);
        bookAdd.setTitle("Book2");
        bookAdd.setPrice(222.22f);
        bookAdd.setAmount(22);
        repository.save(bookAdd);
        bookAdd.setTitle("Book3");
        bookAdd.setPrice(333.33f);
        bookAdd.setAmount(33);
        repository.save(bookAdd);

        Book book = repository.findById(2);

        Assertions.assertEquals(book.getTitle(), "Book2");
        Assertions.assertEquals(book.getPrice(), 222.22f);
        Assertions.assertEquals(book.getAmount(), 22);
    }

    @Test
    void deleteByBooksId() {
        Book bookAdd = new Book();
        bookAdd.setTitle("Book");
        bookAdd.setPrice(123.12f);
        bookAdd.setAmount(50);
        repository.save(bookAdd);
        repository.save(bookAdd);
        repository.save(bookAdd);
        List<Integer> booksId = new ArrayList<>();
        booksId.add(1);
        booksId.add(2);
        booksId.add(3);

        repository.deleteByBooksId(booksId);

        Assertions.assertEquals(repository.getAll().size(), 0);
    }

    @Test
    void getLastId() {
        Book bookAdd = new Book();
        bookAdd.setTitle("Book");
        bookAdd.setPrice(123.12f);
        bookAdd.setAmount(50);
        repository.save(bookAdd);
        repository.save(bookAdd);
        repository.save(bookAdd);

        int id = repository.getLastId();

        Assertions.assertEquals(id, 3);
    }
}