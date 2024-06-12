package repo;

import dbpoll.DataSource;
import dto.UserPost;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepo {

    private UserRepo() {
    }

    public static List<User> getAllUser() {
        List<User> users;
        try (Connection connection = DataSource.getConnection()){
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

    public static boolean haveUser(User user) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "SELECT * FROM \"user\"" +
                    "WHERE " +
                    "user_id = " + user.getUserId() + " AND " +
                    "\"name\" = " + "\'" + user.getName() + "\'" + " AND " +
                    "age = " + user.getAge() + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();

            if (resultSet.next())
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static boolean haveUserById(int id) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "SELECT * FROM \"user\"" +
                    "WHERE user_id = " + id + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();
            if (resultSet.next())
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void deleteUser(User user) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "DELETE FROM \"user\" " +
                    "WHERE user_id = " + user.getUserId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void changeUser(User user) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "UPDATE \"user\" SET name = \'" + user.getName() +
                    "\', age = " + user.getAge() + " WHERE user_id = " + user.getUserId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addUser(UserPost user) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "INSERT INTO \"user\"(\"name\", age) VALUES(\'" + user.getName() + "\', " + user.getAge() + ");";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
