package repo.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import db.DataSource;
import model.Author;
import model.AuthorBook;
import model.Book;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.ext.ScriptUtils;
import org.testcontainers.jdbc.JdbcDatabaseDelegate;
import repo.AuthorBookRepository;
import org.testcontainers.junit.jupiter.Container;
import repo.AuthorRepository;
import repo.BookRepository;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

class AuthorBookRepositoryImplTest {
    private static AuthorBookRepository repository;
    private static BookRepository bookRepository;
    private static AuthorRepository authorRepository;
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
        repository = AuthorBookRepositoryImpl.getInstance();
        bookRepository = BookRepositoryImpl.getInstance();
        authorRepository = AuthorRepositoryImpl.getInstance();
        ScriptUtils.runInitScript(jdbcDatabaseDelegate, initSQLScript);
    }
    @Test
    void getInstance() {
        Assertions.assertEquals(AuthorBookRepositoryImpl.getInstance().getClass(), AuthorBookRepositoryImpl.class);
    }

    @Test
    void findByAuthorId() {
        Book book = new Book();
        bookRepository.save(book);
        bookRepository.save(book);
        bookRepository.save(book);
        Author author = new Author();
        authorRepository.save(author);
        authorRepository.save(author);
        authorRepository.save(author);
        AuthorBook authorBookAdd1 = new AuthorBook();
        authorBookAdd1.setBookId(1);
        authorBookAdd1.setAuthorId(1);
        AuthorBook authorBookAdd2 = new AuthorBook();
        authorBookAdd2.setBookId(2);
        authorBookAdd2.setAuthorId(2);
        AuthorBook authorBookAdd3 = new AuthorBook();
        authorBookAdd3.setBookId(3);
        authorBookAdd3.setAuthorId(3);
        List<AuthorBook> authorBookList = new ArrayList<>();
        authorBookList.add(authorBookAdd1);
        authorBookList.add(authorBookAdd2);
        authorBookList.add(authorBookAdd3);
        repository.saveAll(authorBookList);

        List<AuthorBook> authorsBooks = repository.findByAuthorId(2);

        int bookId = authorsBooks.get(0).getBookId();
        Assertions.assertEquals(authorsBooks.size(), 1);
        Assertions.assertEquals(bookId, 2);
    }

    @Test
    void findByBookId() {
        Book book = new Book();
        bookRepository.save(book);
        bookRepository.save(book);
        bookRepository.save(book);
        Author author = new Author();
        authorRepository.save(author);
        authorRepository.save(author);
        authorRepository.save(author);
        AuthorBook authorBookAdd1 = new AuthorBook();
        authorBookAdd1.setBookId(1);
        authorBookAdd1.setAuthorId(1);
        AuthorBook authorBookAdd2 = new AuthorBook();
        authorBookAdd2.setBookId(2);
        authorBookAdd2.setAuthorId(2);
        AuthorBook authorBookAdd3 = new AuthorBook();
        authorBookAdd3.setBookId(3);
        authorBookAdd3.setAuthorId(3);
        List<AuthorBook> authorBookList = new ArrayList<>();
        authorBookList.add(authorBookAdd1);
        authorBookList.add(authorBookAdd2);
        authorBookList.add(authorBookAdd3);
        repository.saveAll(authorBookList);

        List<AuthorBook> authorsBooks = repository.findByBookId(2);

        int authorId = authorsBooks.get(0).getAuthorId();
        Assertions.assertEquals(authorsBooks.size(), 1);
        Assertions.assertEquals(authorId, 2);
    }

    @Test
    void findByBooksId() {
        Book book = new Book();
        bookRepository.save(book);
        bookRepository.save(book);
        bookRepository.save(book);
        Author author = new Author();
        authorRepository.save(author);
        authorRepository.save(author);
        authorRepository.save(author);
        AuthorBook authorBookAdd1 = new AuthorBook();
        authorBookAdd1.setBookId(1);
        authorBookAdd1.setAuthorId(1);
        AuthorBook authorBookAdd2 = new AuthorBook();
        authorBookAdd2.setBookId(2);
        authorBookAdd2.setAuthorId(2);
        AuthorBook authorBookAdd3 = new AuthorBook();
        authorBookAdd3.setBookId(3);
        authorBookAdd3.setAuthorId(3);
        List<AuthorBook> authorBookList = new ArrayList<>();
        authorBookList.add(authorBookAdd1);
        authorBookList.add(authorBookAdd2);
        authorBookList.add(authorBookAdd3);
        repository.saveAll(authorBookList);
        List<Integer> booksId = new ArrayList<>();
        booksId.add(1);
        booksId.add(2);

        List<AuthorBook> authorsBooks = repository.findByBooksId(booksId);


        Assertions.assertEquals(authorsBooks.size(), 2);
    }

    @Test
    void deleteAllFromBookId() {
        Book book = new Book();
        bookRepository.save(book);
        Author author = new Author();
        authorRepository.save(author);
        authorRepository.save(author);
        authorRepository.save(author);
        AuthorBook authorBookAdd1 = new AuthorBook();
        authorBookAdd1.setBookId(1);
        authorBookAdd1.setAuthorId(1);
        AuthorBook authorBookAdd2 = new AuthorBook();
        authorBookAdd2.setBookId(1);
        authorBookAdd2.setAuthorId(2);
        AuthorBook authorBookAdd3 = new AuthorBook();
        authorBookAdd3.setBookId(1);
        authorBookAdd3.setAuthorId(3);
        List<AuthorBook> authorBookList = new ArrayList<>();
        authorBookList.add(authorBookAdd1);
        authorBookList.add(authorBookAdd2);
        authorBookList.add(authorBookAdd3);
        repository.saveAll(authorBookList);

        repository.deleteAllFromBookId(1);


        Assertions.assertEquals(repository.findByBookId(1), null);
    }

    @Test
    void saveAll() {
        Book book = new Book();
        bookRepository.save(book);
        Author author = new Author();
        authorRepository.save(author);
        authorRepository.save(author);
        authorRepository.save(author);
        AuthorBook authorBookAdd1 = new AuthorBook();
        authorBookAdd1.setBookId(1);
        authorBookAdd1.setAuthorId(1);
        AuthorBook authorBookAdd2 = new AuthorBook();
        authorBookAdd2.setBookId(1);
        authorBookAdd2.setAuthorId(2);
        AuthorBook authorBookAdd3 = new AuthorBook();
        authorBookAdd3.setBookId(1);
        authorBookAdd3.setAuthorId(3);
        List<AuthorBook> authorBookList = new ArrayList<>();
        authorBookList.add(authorBookAdd1);
        authorBookList.add(authorBookAdd2);
        authorBookList.add(authorBookAdd3);

        repository.saveAll(authorBookList);


        Assertions.assertEquals(repository.findByBookId(1).size(), 3);
    }

    @Test
    void deleteAllFromBooksId() {
        Book book = new Book();
        bookRepository.save(book);
        bookRepository.save(book);
        bookRepository.save(book);
        Author author = new Author();
        authorRepository.save(author);
        authorRepository.save(author);
        authorRepository.save(author);
        AuthorBook authorBookAdd1 = new AuthorBook();
        authorBookAdd1.setBookId(1);
        authorBookAdd1.setAuthorId(1);
        AuthorBook authorBookAdd2 = new AuthorBook();
        authorBookAdd2.setBookId(2);
        authorBookAdd2.setAuthorId(2);
        AuthorBook authorBookAdd3 = new AuthorBook();
        authorBookAdd3.setBookId(3);
        authorBookAdd3.setAuthorId(3);
        List<AuthorBook> authorBookList = new ArrayList<>();
        authorBookList.add(authorBookAdd1);
        authorBookList.add(authorBookAdd2);
        authorBookList.add(authorBookAdd3);
        repository.saveAll(authorBookList);
        List<Integer> booksId = new ArrayList<>();
        booksId.add(1);
        booksId.add(2);

        repository.deleteAllFromBooksId(booksId);


        Assertions.assertEquals(repository.findByBookId(1), null);
        Assertions.assertEquals(repository.findByBookId(2), null);
    }
}