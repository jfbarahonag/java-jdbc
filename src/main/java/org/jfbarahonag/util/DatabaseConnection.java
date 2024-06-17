package org.jfbarahonag.util;

import org.apache.commons.dbcp2.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/project";
    private static final String user = "admin";
    private static final String pass = ".admin123";
    private static BasicDataSource poolConnection;

    public static BasicDataSource getInstance() {
        if (poolConnection == null) {
            poolConnection = new BasicDataSource();
            poolConnection.setUrl(url);
            poolConnection.setUsername(user);
            poolConnection.setPassword(pass);

            poolConnection.setInitialSize(3);
            poolConnection.setMinIdle(3);
            poolConnection.setMaxIdle(10);
            poolConnection.setMaxTotal(10);
        }
        return poolConnection;
    }

    public static Connection getConnection() throws SQLException {
        return getInstance().getConnection();
    }
}
