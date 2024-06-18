package repo.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import db.DataSource;
import model.Book;
import model.BuyBook;
import model.User;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import org.testcontainers.junit.jupiter.Container;
import repo.BookRepository;
import repo.BuyBookRepository;
import repo.UserRepository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

class BuyBookRepositoryImplTest {
    private static BuyBookRepository repository;
    private static BookRepository bookRepository;
    private static UserRepository userRepository;
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
        repository = BuyBookRepositoryImpl.getInstance();
        bookRepository = BookRepositoryImpl.getInstance();
        userRepository = UserRepositoryImpl.getInstance();
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, initSQLScript);
    }
    @Test
    void getInstance() {
        Assertions.assertEquals(BuyBookRepositoryImpl.getInstance().getClass(), BuyBookRepositoryImpl.class);
    }

    @Test
    void save() {
        Book book = new Book();
        bookRepository.save(book);
        User user = new User();
        userRepository.save(user);
        BuyBook buyBookAdd = new BuyBook();
        buyBookAdd.setBookId(1);
        buyBookAdd.setUserId(1);
        buyBookAdd.setPrice(111.11f);
        buyBookAdd.setAmount(11);

        repository.save(buyBookAdd);

        List<BuyBook> buyBooksDB = repository.getAll();
        BuyBook buyBookAddFromDB = buyBooksDB.get(0);
        Assertions.assertEquals(buyBookAddFromDB.getBookId(), buyBookAdd.getBookId());
        Assertions.assertEquals(buyBookAddFromDB.getUserId(), buyBookAdd.getUserId());
        Assertions.assertEquals(buyBookAddFromDB.getPrice(), buyBookAdd.getPrice());
        Assertions.assertEquals(buyBookAddFromDB.getAmount(), buyBookAdd.getAmount());
    }

    @Test
    void update() {
        Book book = new Book();
        bookRepository.save(book);
        bookRepository.save(book);
        User user = new User();
        userRepository.save(user);
        userRepository.save(user);
        BuyBook buyBookAdd = new BuyBook();
        buyBookAdd.setBookId(1);
        buyBookAdd.setUserId(1);
        buyBookAdd.setPrice(111.11f);
        buyBookAdd.setAmount(11);
        repository.save(buyBookAdd);
        List<BuyBook> buyBooksDB = repository.getAll();
        BuyBook buyBookAddFromDB = buyBooksDB.get(0);
        BuyBook buyBookUpdate = new BuyBook();
        buyBookUpdate.setBuyBookId(buyBookAddFromDB.getBuyBookId());
        buyBookUpdate.setBookId(2);
        buyBookUpdate.setUserId(2);
        buyBookUpdate.setPrice(222.22f);
        buyBookUpdate.setAmount(22);

        repository.update(buyBookUpdate);


        BuyBook buyBookUpdateFromDB = repository.findById(buyBookUpdate.getBuyBookId());
        Assertions.assertEquals(buyBookUpdateFromDB.getBookId(), buyBookUpdate.getBookId());
        Assertions.assertEquals(buyBookUpdateFromDB.getUserId(), buyBookUpdate.getUserId());
        Assertions.assertEquals(buyBookUpdateFromDB.getPrice(), buyBookUpdate.getPrice());
        Assertions.assertEquals(buyBookUpdateFromDB.getAmount(), buyBookUpdate.getAmount());
    }

    @Test
    void delete() {
        Book book = new Book();
        bookRepository.save(book);
        User user = new User();
        userRepository.save(user);
        BuyBook buyBookAdd = new BuyBook();
        buyBookAdd.setBookId(1);
        buyBookAdd.setUserId(1);
        buyBookAdd.setPrice(111.11f);
        buyBookAdd.setAmount(11);
        repository.save(buyBookAdd);
        List<BuyBook> buyBooksDB = repository.getAll();
        BuyBook buyBookAddFromDB = buyBooksDB.get(0);
        BuyBook buyBookDelete = new BuyBook();
        buyBookDelete.setBuyBookId(buyBookAddFromDB.getBuyBookId());

        repository.delete(buyBookDelete);

        Assertions.assertEquals(repository.getAll().size(), 0);
    }

    @Test
    void getAll() {
        Book book = new Book();
        bookRepository.save(book);
        User user = new User();
        userRepository.save(user);
        BuyBook buyBookAdd = new BuyBook();
        buyBookAdd.setBookId(1);
        buyBookAdd.setUserId(1);
        buyBookAdd.setPrice(111.11f);
        buyBookAdd.setAmount(11);
        repository.save(buyBookAdd);
        repository.save(buyBookAdd);
        repository.save(buyBookAdd);

        List<BuyBook> buyBooks = repository.getAll();

        Assertions.assertEquals(buyBooks.size(), 3);
    }

    @Test
    void findById() {
        Book book = new Book();
        bookRepository.save(book);
        bookRepository.save(book);
        bookRepository.save(book);
        User user = new User();
        userRepository.save(user);
        userRepository.save(user);
        userRepository.save(user);
        BuyBook buyBookAdd = new BuyBook();
        buyBookAdd.setBookId(1);
        buyBookAdd.setUserId(1);
        buyBookAdd.setPrice(111.11f);
        buyBookAdd.setAmount(11);
        repository.save(buyBookAdd);
        buyBookAdd.setBookId(2);
        buyBookAdd.setUserId(2);
        buyBookAdd.setPrice(222.22f);
        buyBookAdd.setAmount(22);
        repository.save(buyBookAdd);
        buyBookAdd.setBookId(3);
        buyBookAdd.setUserId(3);
        buyBookAdd.setPrice(333.33f);
        buyBookAdd.setAmount(33);
        repository.save(buyBookAdd);

        BuyBook buyBook = repository.findById(2);

        Assertions.assertEquals(buyBook.getBookId(), 2);
        Assertions.assertEquals(buyBook.getUserId(), 2);
        Assertions.assertEquals(buyBook.getPrice(), 222.22f);
        Assertions.assertEquals(buyBook.getAmount(), 22);
    }

    @Test
    void deleteByUserId() {
        Book book = new Book();
        bookRepository.save(book);
        User user = new User();
        userRepository.save(user);
        userRepository.save(user);
        BuyBook buyBookAdd = new BuyBook();
        buyBookAdd.setBookId(1);
        buyBookAdd.setUserId(1);
        buyBookAdd.setPrice(111.11f);
        buyBookAdd.setAmount(11);
        repository.save(buyBookAdd);
        repository.save(buyBookAdd);
        buyBookAdd.setUserId(2);
        repository.save(buyBookAdd);

        repository.deleteByUserId(1);

        Assertions.assertEquals(repository.getAll().size(), 1);
    }

    @Test
    void deleteByBookId() {
        Book book = new Book();
        bookRepository.save(book);
        bookRepository.save(book);
        User user = new User();
        userRepository.save(user);
        BuyBook buyBookAdd = new BuyBook();
        buyBookAdd.setBookId(1);
        buyBookAdd.setUserId(1);
        buyBookAdd.setPrice(111.11f);
        buyBookAdd.setAmount(11);
        repository.save(buyBookAdd);
        repository.save(buyBookAdd);
        buyBookAdd.setBookId(2);
        repository.save(buyBookAdd);

        repository.deleteByBookId(1);

        Assertions.assertEquals(repository.getAll().size(), 1);
    }

    @Test
    void deleteByBooksId() {
        Book book = new Book();
        bookRepository.save(book);
        bookRepository.save(book);
        User user = new User();
        userRepository.save(user);
        BuyBook buyBookAdd = new BuyBook();
        buyBookAdd.setBookId(1);
        buyBookAdd.setUserId(1);
        buyBookAdd.setPrice(111.11f);
        buyBookAdd.setAmount(11);
        repository.save(buyBookAdd);
        repository.save(buyBookAdd);
        buyBookAdd.setBookId(2);
        repository.save(buyBookAdd);
        List<Integer> booksId = new ArrayList<>();
        booksId.add(1);
        booksId.add(2);

        repository.deleteByBooksId(booksId);

        Assertions.assertEquals(repository.getAll().size(), 0);
    }
}