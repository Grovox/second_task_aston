package repo;

import dbpoll.DataSource;
import dto.BookPost;
import model.Author;
import model.Book;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookRepo {

    private BookRepo() {
    }

    public static List<Book> getAllBook() {
        List<Book> books;
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "SELECT * FROM book;";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();

            books = new ArrayList<>();
            Book book;
            while (resultSet.next()) {
                book = new Book();
                book.setBookId(resultSet.getInt(1));
                book.setTitle(resultSet.getString(2));
                book.setPrice(resultSet.getFloat(3));
                book.setAmount(resultSet.getInt(4));

                sqlQuery = "SELECT author.author_id, author.name " +
                        "FROM author INNER JOIN author_book USING (author_id) " +
                        "INNER JOIN book USING (book_id) WHERE book.book_id = " + book.getBookId() + ";";
                ResultSet resultSetTwo = connection.prepareStatement(sqlQuery).executeQuery();
                List<Author> authors = new ArrayList<>();
                Author author;
                while (resultSetTwo.next()) {
                    author = new Author();
                    author.setAuthorId(resultSetTwo.getInt(1));
                    author.setName(resultSetTwo.getString(2));
                    authors.add(author);
                }
                book.setAuthors(authors);
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public static boolean haveBookById(int id) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "SELECT * FROM book " +
                    "WHERE book_id = " + id + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();

            if (resultSet.next())
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void changeBook(Book book) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "UPDATE book SET " +
                    "title = \'" + book.getTitle() +
                    "\', price = " + book.getPrice() +
                    " , amount = " + book.getAmount() +
                    " WHERE book_id = " + book.getBookId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
            sqlQuery = "DELETE FROM author_book " +
                    "WHERE book_id = " + book.getBookId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
            for (Author author : book.getAuthors()) {
                sqlQuery = "INSERT INTO author_book(book_id, author_id) VALUES(" + book.getBookId() + ", " + author.getAuthorId() + ");";
                connection.prepareStatement(sqlQuery).executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addBook(BookPost book) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "INSERT INTO book(title, price, amount) VALUES(\'" + book.getTitle() + "\', " + book.getPrice() + ", " + book.getAmount() + ");";
            connection.prepareStatement(sqlQuery).executeUpdate();
            sqlQuery = "SELECT book_id FROM book ORDER BY book_id DESC LIMIT 1;";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();
            resultSet.next();
            int bookId = resultSet.getInt(1);
            for (Author author : book.getAuthors()) {
                sqlQuery = "INSERT INTO author_book(book_id, author_id) VALUES(" + bookId + ", " + author.getAuthorId() + ");";
                connection.prepareStatement(sqlQuery).executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean haveBook(Book book) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "SELECT * FROM book " +
                    "WHERE book_id = " + book.getBookId() +
                    " AND title = " + "\'" + book.getTitle() +
                    "\' AND price = " + book.getPrice() +
                    " AND amount = " + book.getAmount() + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();
            if (!resultSet.next())
                return false;
            for (Author author : book.getAuthors()) {
                sqlQuery = "SELECT * FROM author_book " +
                        " WHERE book_id = " + book.getBookId() +
                        " AND author_id = " + author.getAuthorId() + ";";
                if (!connection.prepareStatement(sqlQuery).executeQuery().next())
                    return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static void deleteBook(Book book) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "DELETE FROM author_book " +
                    "WHERE book_id = " + book.getBookId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();

            sqlQuery = "DELETE FROM buy_book " +
                    "WHERE book_id = " + book.getBookId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();

            for (Author author : book.getAuthors()) {
                sqlQuery = "SELECT * FROM author_book " +
                        " WHERE author_id = " + author.getAuthorId() + ";";
                if (!connection.prepareStatement(sqlQuery).executeQuery().next()) {
                    sqlQuery = "DELETE FROM author" +
                            " WHERE author_id = " + author.getAuthorId() + ";";
                    connection.prepareStatement(sqlQuery).executeUpdate();
                }
            }

            sqlQuery = "DELETE FROM buy_book " +
                    "WHERE book_id = " + book.getBookId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();

            sqlQuery = "DELETE FROM book " +
                    "WHERE book_id = " + book.getBookId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getAmountBookById(int bookId) {
        int amount = 0;
        try (Connection connection = DataSource.getConnection()) {
            String sqlQuery = "SELECT amount FROM book " +
                    "WHERE book_id = " + bookId + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();
            resultSet.next();
            amount = resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return amount;
    }

    public static void changeBookAmountById(int amount, int bookId) {
        try (Connection connection = DataSource.getConnection()) {
            String sqlQuery = "UPDATE book SET " +
                    "amount = amount - " + amount +
                    " WHERE book_id = " + bookId + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
