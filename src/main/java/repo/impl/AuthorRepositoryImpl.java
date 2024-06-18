package repo.impl;

import db.DataSource;
import model.Author;
import repo.AuthorRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AuthorRepositoryImpl implements AuthorRepository {
    private static AuthorRepository instance;
    private static DataSource dataSource;

    private AuthorRepositoryImpl() {
        dataSource = DataSource.getInstance();
    }

    public static synchronized AuthorRepository getInstance() {
        if (instance == null) {
            instance = new AuthorRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void save(Author author) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO author(\"name\") VALUES(\'" + author.getName() + "\');";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Author author) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "UPDATE author SET name = \'" + author.getName() +
                    "\' WHERE author_id = " + author.getAuthorId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Author author) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DELETE FROM author " +
                    "WHERE author_id = " + author.getAuthorId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Author> getAll() {
        List<Author> authors;
        try (Connection connection = dataSource.getConnection()) {
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

    @Override
    public Author findById(int id) {
        Author author;
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT * FROM author " +
                    "WHERE author_id = " + id + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();
            if (!resultSet.next()) return null;
            author = new Author();
            author.setAuthorId(resultSet.getInt(1));
            author.setName(resultSet.getString(2));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return author;
    }

    @Override
    public void deleteAuthorByIdIfNotHaveBooks(List<Integer> authorsId) {
        if (authorsId.isEmpty()) return;
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DELETE FROM author " +
                    "WHERE author_id IN( ";
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(sqlQuery);
            stringBuilder.append(authorsId.get(0));
            for (int i = 1; i < authorsId.size(); i++)
                stringBuilder.append(", " + authorsId.get(i));
            stringBuilder.append(") AND NOT author_id IN(SELECT author_id FROM author_book);");
            sqlQuery = stringBuilder.toString();

            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
