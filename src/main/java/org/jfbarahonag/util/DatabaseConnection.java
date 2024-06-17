package org.jfbarahonag.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String url = "jdbc:mysql://localhost:3306/project";
    private static final String user = "admin";
    private static final String pass = ".admin123";
    private static Connection connection;

    public static Connection getInstance() throws SQLException {
        if (connection == null) {
            connection = DriverManager.getConnection(url, user, pass);
            connection.setAutoCommit(false);
        }
        return connection;
    }
}
