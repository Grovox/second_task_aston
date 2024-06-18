package repo.impl;

import db.DataSource;
import model.AuthorBook;
import repo.AuthorBookRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorBookRepositoryImpl implements AuthorBookRepository {
    private static AuthorBookRepository instance;
    private static DataSource dataSource;

    private AuthorBookRepositoryImpl() {
        dataSource = DataSource.getInstance();
    }


    public static synchronized AuthorBookRepository getInstance() {
        if (instance == null) {
            instance = new AuthorBookRepositoryImpl();
        }
        return instance;
    }

    @Override
    public List<AuthorBook> findByAuthorId(int id) {
        List<AuthorBook> authorBooks;
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT book_id, author_id FROM author_book WHERE author_id = " + id + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();

            authorBooks = new ArrayList<>();
            AuthorBook authorBook;
            while (resultSet.next()) {
                authorBook = new AuthorBook();
                authorBook.setBookId(resultSet.getInt(1));
                authorBook.setAuthorId(resultSet.getInt(2));
                authorBooks.add(authorBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (authorBooks.isEmpty()) return null;
        return authorBooks;
    }

    @Override
    public List<AuthorBook> findByBookId(int id) {
        List<AuthorBook> authorBooks;
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT book_id, author_id FROM author_book WHERE book_id = " + id + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();

            authorBooks = new ArrayList<>();
            AuthorBook authorBook;
            while (resultSet.next()) {
                authorBook = new AuthorBook();
                authorBook.setBookId(resultSet.getInt(1));
                authorBook.setAuthorId(resultSet.getInt(2));
                authorBooks.add(authorBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (authorBooks.isEmpty()) return null;
        return authorBooks;
    }

    @Override
    public List<AuthorBook> findByBooksId(List<Integer> booksId) {
        if (booksId.isEmpty()) return null;
        List<AuthorBook> authorBooks;
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT book_id, author_id FROM author_book WHERE book_id IN(";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sqlQuery);
            stringBuilder.append(booksId.get(0));
            for (int i = 1; i < booksId.size(); i++)
                stringBuilder.append(", " + booksId.get(i));
            stringBuilder.append(");");
            sqlQuery = stringBuilder.toString();

            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();

            authorBooks = new ArrayList<>();
            AuthorBook authorBook;
            while (resultSet.next()) {
                authorBook = new AuthorBook();
                authorBook.setBookId(resultSet.getInt(1));
                authorBook.setAuthorId(resultSet.getInt(2));
                authorBooks.add(authorBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if (authorBooks.isEmpty()) return null;
        return authorBooks;
    }

    @Override
    public void deleteAllFromBookId(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DELETE FROM author_book " +
                    "WHERE book_id = " + id + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveAll(List<AuthorBook> authorBooks) {
        if (authorBooks.isEmpty()) return;
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO author_book(author_id, book_id) VALUES";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sqlQuery);
            stringBuilder.append("( " + authorBooks.get(0).getAuthorId() + ", " + authorBooks.get(0).getBookId() + ")");
            for (int i = 1; i < authorBooks.size(); i++)
                stringBuilder.append(", ( " + authorBooks.get(i).getAuthorId() + ", " + authorBooks.get(i).getBookId() + ")");
            stringBuilder.append(";");
            sqlQuery = stringBuilder.toString();
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAllFromBooksId(List<Integer> booksId) {
        if (booksId.isEmpty()) return;
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DELETE FROM author_book " +
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
}
