package by.expert.soft.dao.pool;

import java.util.ResourceBundle;

public final class ConnectionConfig {

    public static ResourceBundle bundle = ResourceBundle.getBundle("dbConfig");

    private ConnectionConfig() {
    }

    public static String getDriver() {
        return bundle.getString("database.driver");
    }

    public static String getDBURL() {
        String url = bundle.getString("database.url") + "/" + bundle.getString("database.name") + "?" +
                bundle.getString("database.options");
        return url;
    }

    public static String getUserName() {
        return bundle.getString("database.username");
    }

    public static String getPassword() {
        return bundle.getString("database.password");
    }

    public static String getPoolSize() {
        return bundle.getString("database.pool.size");
    }
}
