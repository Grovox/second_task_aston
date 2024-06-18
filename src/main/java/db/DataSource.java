package db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import repo.AuthorBookRepository;
import repo.UserRepository;
import repo.impl.UserRepositoryImpl;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static String configFile = "/db.properties";
    private static HikariConfig config = new HikariConfig(configFile);
    private static HikariDataSource ds = new HikariDataSource(config);
    private static DataSource instance;

    private DataSource() {
    }

    public static synchronized DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public synchronized Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
