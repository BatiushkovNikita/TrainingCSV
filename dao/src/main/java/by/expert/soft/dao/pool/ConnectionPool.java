package by.expert.soft.dao.pool;

import by.expert.soft.dao.exception.DaoException;
import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;

public enum ConnectionPool {
    POOL;
    private Logger Log = LogManager.getLogger(ConnectionPool.class.getName());

    private BasicDataSource dataSource;

    private ConnectionPool() {
        dataSource = new BasicDataSource();
        dataSource.setDriverClassName(ConnectionConfig.getDriver());
        dataSource.setUrl(ConnectionConfig.getDBURL());
        dataSource.setUsername(ConnectionConfig.getUserName());
        dataSource.setPassword(ConnectionConfig.getPassword());
    }

    public Connection getConnection() throws DaoException {
        Connection connection;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            Log.error("Cannot get connection");
            throw new DaoException("Cannot get connection", e);
        }
        return connection;
    }
}
