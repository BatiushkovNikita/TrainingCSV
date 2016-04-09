package by.expert.soft.dao.pool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.Semaphore;

public enum ConnectionManager {
    POOL;
    public final int POOL_SIZE = Integer.valueOf(ConnectionConfig.getPoolSize());

    private static Logger Log = LogManager.getLogger(ConnectionManager.class.getName());
    private Semaphore semaphore = new Semaphore(POOL_SIZE);

    static {
        try {
            Class.forName(ConnectionConfig.getDriver());
        } catch (ClassNotFoundException e) {
            Log.error("Cannot load JDBC driver", e);
        }
    }

    public Connection getConnection() {
        String dbURL = ConnectionConfig.getDBURL();
        String username = ConnectionConfig.getUserName();
        String password = ConnectionConfig.getPassword();
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbURL, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            semaphore.acquire();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return connection;
    }

    public void closeConnection(Connection connection) {
        try {
            connection.close();
            semaphore.release();
        } catch (SQLException e) {
            Log.error("Cannot close Connection", e);
        } finally {
            semaphore.release();
        }
    }
}
