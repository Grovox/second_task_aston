package repo;

import dbpoll.DataSource;
import dto.BuyBookPost;
import model.BuyBook;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyBookRepo {
    private BuyBookRepo() {
    }

    public static void deleteBuyBookByUserId(User user) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "DELETE FROM buy_book WHERE user_id = " + user.getUserId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<BuyBook> getAllBuyBooks() {
        List<BuyBook> buyBooks;
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "SELECT * FROM buy_book;";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();

            buyBooks = new ArrayList<>();
            BuyBook buyBook;
            while (resultSet.next()) {
                buyBook = new BuyBook();
                buyBook.setBuyBookId(resultSet.getInt(1));
                buyBook.setUserId(resultSet.getInt(2));
                buyBook.setBookId(resultSet.getInt(3));
                buyBook.setPrice(resultSet.getFloat(4));
                buyBook.setAmount(resultSet.getInt(5));
                buyBooks.add(buyBook);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return buyBooks;
    }

    public static boolean haveBuyBookById(int id) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "SELECT * FROM buy_book " +
                    "WHERE buy_book_id = " + id + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();

            if (resultSet.next())
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void changeBuyBook(BuyBook buyBook) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "UPDATE buy_book SET book_id = " + buyBook.getBookId() +
                    ", user_id = " + buyBook.getUserId() +
                    ", price = " + buyBook.getPrice() +
                    ", amount = " + buyBook.getAmount() +
                    " WHERE buy_book_id = " + buyBook.getBuyBookId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static void addBuyBook(BuyBookPost buyBook) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "SELECT price FROM book " +
                    "WHERE book_id = " + buyBook.getBookId() + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();
            resultSet.next();
            float price = resultSet.getInt(1);
            sqlQuery = "INSERT INTO buy_book(user_id, book_id, price, amount) VALUES(" + buyBook.getUserId() +
                    ", " + buyBook.getBookId() +
                    ", " + price +
                    ", " + buyBook.getAmount() + ");";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean haveBuyBook(BuyBook buyBook) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "SELECT * FROM buy_book " +
                    "WHERE " +
                    "user_id = " + buyBook.getUserId() + " AND " +
                    "book_id = " + buyBook.getBookId() + " AND " +
                    "price = " + buyBook.getPrice() + " AND " +
                    "amount = " + buyBook.getAmount() + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();

            if (resultSet.next())
                return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public static void deleteBuyBook(BuyBook buyBook) {
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "DELETE FROM buy_book " +
                    "WHERE buy_book_id = " + buyBook.getBuyBookId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static int getAmountBuyBookById(int buyBookId) {
        int amount = 0;
        try (Connection connection = DataSource.getConnection()){
            String sqlQuery = "SELECT amount FROM buy_book " +
                    "WHERE buy_book_id = " + buyBookId + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();
            resultSet.next();
            amount = resultSet.getInt(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return amount;
    }
}
