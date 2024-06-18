package repo.impl;

import db.DataSource;
import model.User;
import repo.UserRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository {
    private static UserRepository instance;
    private static DataSource dataSource;

    private UserRepositoryImpl() {
        dataSource = DataSource.getInstance();
    }

    public static synchronized UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepositoryImpl();
        }
        return instance;
    }
    @Override
    public void save(User user) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO \"user\"(\"name\", age) VALUES('" + user.getName() + "', " + user.getAge() + ");";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(User user) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "UPDATE \"user\" SET name = '" + user.getName() +
                    "', age = " + user.getAge() + " WHERE user_id = " + user.getUserId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(User user) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DELETE FROM \"user\" " +
                    "WHERE user_id = " + user.getUserId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> users;
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT * FROM \"user\";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();

            users = new ArrayList<>();
            User user;
            while (resultSet.next()) {
                user = new User();
                user.setUserId(resultSet.getInt(1));
                user.setName(resultSet.getString(2));
                user.setAge(resultSet.getInt(3));
                users.add(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    @Override
    public User findById(int id) {
        User user;
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT * FROM \"user\"" +
                    "WHERE user_id = " + id + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();
            if (!resultSet.next()) return null;
            user = new User();
            user.setUserId(resultSet.getInt(1));
            user.setName(resultSet.getString(2));
            user.setAge(resultSet.getInt(3));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
