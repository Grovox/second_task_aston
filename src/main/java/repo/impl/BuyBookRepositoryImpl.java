package repo.impl;

import db.DataSource;
import model.BuyBook;
import repo.BuyBookRepository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyBookRepositoryImpl implements BuyBookRepository {
    private static BuyBookRepository instance;
    private static DataSource dataSource;

    private BuyBookRepositoryImpl() {
        dataSource = DataSource.getInstance();
    }

    public static synchronized BuyBookRepository getInstance() {
        if (instance == null) {
            instance = new BuyBookRepositoryImpl();
        }
        return instance;
    }

    @Override
    public void save(BuyBook buyBook) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "INSERT INTO buy_book(user_id, book_id, price, amount) VALUES(" + buyBook.getUserId() +
                    ", " + buyBook.getBookId() +
                    ", " + buyBook.getPrice() +
                    ", " + buyBook.getAmount() + ");";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(BuyBook buyBook) {
        try (Connection connection = dataSource.getConnection()) {
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

    @Override
    public void delete(BuyBook buyBook) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DELETE FROM buy_book " +
                    "WHERE buy_book_id = " + buyBook.getBuyBookId() + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<BuyBook> getAll() {
        List<BuyBook> buyBooks;
        try (Connection connection = dataSource.getConnection()) {
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

    @Override
    public BuyBook findById(int id) {
        BuyBook buyBook;
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "SELECT * FROM buy_book " +
                    "WHERE buy_book_id = " + id + ";";
            ResultSet resultSet = connection.prepareStatement(sqlQuery).executeQuery();
            if (!resultSet.next()) return null;
            buyBook = new BuyBook();
            buyBook.setBuyBookId(resultSet.getInt(1));
            buyBook.setUserId(resultSet.getInt(2));
            buyBook.setBookId(resultSet.getInt(3));
            buyBook.setPrice(resultSet.getFloat(4));
            buyBook.setAmount(resultSet.getInt(5));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return buyBook;
    }

    @Override
    public void deleteByUserId(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DELETE FROM buy_book WHERE user_id = " + id + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByBookId(int id) {
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DELETE FROM buy_book WHERE book_id = " + id + ";";
            connection.prepareStatement(sqlQuery).executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteByBooksId(List<Integer> booksId) {
        if (booksId.isEmpty()) return;
        try (Connection connection = dataSource.getConnection()) {
            String sqlQuery = "DELETE FROM buy_book WHERE book_id IN(";
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
