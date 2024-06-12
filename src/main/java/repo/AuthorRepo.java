package repo;

import dbpoll.DataSource;
import dto.AuthorPost;
import model.Author;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepo {

    private AuthorRepo() {
    }

    public static List<Author> getAllAuthors() {
        List<Author> authors;
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "SELECT * FROM author;";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();

            authors = new ArrayList<>();
            Author author;
            while (resultSet.next()) {
                author = new Author();
                author.setAuthorId(resultSet.getInt(1));
                author.setName(resultSet.getString(2));
                authors.add(author);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return authors;
    }

    public static boolean haveAuthorById(int id) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "SELECT * FROM author " +
                    "WHERE author_id = " + id + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();

            if (resultSet.next())
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void changeAuthor(Author author) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "UPDATE author SET name = \'" + author.getName() +
                    "\' WHERE author_id = " + author.getAuthorId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addAuthor(AuthorPost author) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "INSERT INTO author(\"name\") VALUES(\'" + author.getName() + "\');";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean haveAuthor(Author author) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "SELECT * FROM author " +
                    "WHERE author_id = " + author.getAuthorId() +
                    " AND name = " + "\'" + author.getName() + "\';";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();

            if (resultSet.next())
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void deleteAuthor(Author author) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "SELECT book_id FROM author_book " +
                    "WHERE author_id = " + author.getAuthorId() + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();

            while (resultSet.next()) {
                int bookId = resultSet.getInt(1);
                sqlQuery = "DELETE FROM author_book " +
                        "WHERE book_id = " + bookId + ";";
                connection.prepareStatement(sqlQuery).executeUpdate();
                sqlQuery = "DELETE FROM buy_book " +
                        "WHERE book_id = " + bookId + ";";
                connection.prepareStatement(sqlQuery).executeUpdate();
                sqlQuery = "DELETE FROM book " +
                        "WHERE book_id = " + bookId + ";";
                connection.prepareStatement(sqlQuery).executeUpdate();
            }

            sqlQuery = "DELETE FROM author " +
                    "WHERE author_id = " + author.getAuthorId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
