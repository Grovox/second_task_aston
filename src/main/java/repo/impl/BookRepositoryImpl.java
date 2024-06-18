package repo.impl;

import db.DataSource;
import dto.BookGet;
import model.Author;
import model.Book;
import repo.BookRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    private static BookRepository instance;
    private static DataSource dataSource;

    private BookRepositoryImpl() {
        dataSource = DataSource.getInstance();
    }

    public static synchronized BookRepository getInstance() {
        if (instance == null) {
            instance = new BookRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void save(Book book) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO book(title, price, amount) VALUES(\'" + book.getTitle() + "\', " + book.getPrice() + ", " + book.getAmount() + ");";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Book book) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "UPDATE book SET " +
                    "title = \'" + book.getTitle() +
                    "\', price = " + book.getPrice() +
                    " , amount = " + book.getAmount() +
                    " WHERE book_id = " + book.getBookId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Book book) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DELETE FROM book " +
                    "WHERE book_id = " + book.getBookId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BookGet> getAll() {
        List<BookGet> booksGet;
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT * FROM book;";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();

            booksGet = new ArrayList<>();
            BookGet bookGet;
            while (resultSet.next()) {
                bookGet = new BookGet();
                bookGet.setBookId(resultSet.getInt(1));
                bookGet.setTitle(resultSet.getString(2));
                bookGet.setPrice(resultSet.getFloat(3));
                bookGet.setAmount(resultSet.getInt(4));

                sqlQuery = "SELECT author.author_id, author.name " +
                        "FROM author INNER JOIN author_book USING (author_id) " +
                        "INNER JOIN book USING (book_id) WHERE book.book_id = " + bookGet.getBookId() + ";";
                ResultSet resultSetTwo = connection.prepareStatement(sqlQuery).executeQuery();
                List<Author> authors = new ArrayList<>();
                Author author;
                while (resultSetTwo.next()) {
                    author = new Author();
                    author.setAuthorId(resultSetTwo.getInt(1));
                    author.setName(resultSetTwo.getString(2));
                    authors.add(author);
                }
                bookGet.setAuthors(authors);
                booksGet.add(bookGet);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return booksGet;
    }

    @Override
    public Book findById(int id) {
        Book book;
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT * FROM book " +
                    "WHERE book_id = " + id + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();
            if (!resultSet.next()) return null;
            book = new Book();
            book.setBookId(resultSet.getInt(1));
            book.setTitle(resultSet.getString(2));
            book.setPrice(resultSet.getFloat(3));
            book.setAmount(resultSet.getInt(4));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return book;
    }

    @Override
    public void deleteByBooksId(List<Integer> booksId) {
        if (booksId.isEmpty()) return;
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DELETE FROM book " +
                    "WHERE book_id IN(";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sqlQuery);
            stringBuilder.append(booksId.get(0));
            for (int i = 1; i < booksId.size(); i++)
                stringBuilder.append(", " + booksId.get(i));
            stringBuilder.append(");");
            sqlQuery = stringBuilder.toString();
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getLastId() {
        int id;
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT book_id FROM book ORDER BY book_id DESC LIMIT 1;";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();
            resultSet.next();
            id = resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }
}
