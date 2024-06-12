package dbpoll;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DataSource {
    private static String configFile = "/db.properties";
    private static HikariConfig config = new HikariConfig(configFile);
    private static HikariDataSource ds = new HikariDataSource(config);

    private DataSource() {
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}
