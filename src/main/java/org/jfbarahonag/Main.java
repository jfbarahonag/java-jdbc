package org.jfbarahonag;

import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {
        Connection myConnection = null;
        Statement myStatement = null;
        PreparedStatement myPreparedStatement;
        ResultSet myResultSet = null;

        String url = "jdbc:mysql://localhost:3306/project";
        String user = "admin";
        String pass = ".admin123";

        enum Example {
            CASE_1,
            CASE_2,
            CASE_3,
            CASE_4,
        }

        Example example = Example.CASE_4;

        try {

            myConnection = DriverManager.getConnection(
                    url,
                    user,
                    pass
            );

            System.out.println("Database connected");

            if (example == Example.CASE_1) {
                myStatement = myConnection.createStatement();
                myResultSet = myStatement.executeQuery("SELECT * FROM employees");

                while (myResultSet.next()) {
                    System.out.println(myResultSet.getString("first_name"));
                }
                return;
            }

            if (example == Example.CASE_2) {
                String sqlStatemet = "INSERT INTO employees (first_name, last_name) VALUES (?, ?)";
                myPreparedStatement = myConnection.prepareStatement(sqlStatemet);
                myPreparedStatement.setString(1, "Pepito");
                myPreparedStatement.setString(2, "Perez Sosa");

                int rowsAffected = myPreparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("New employee has been added");
                }
            }

            if (example == Example.CASE_3) {
                myStatement = myConnection.createStatement();
                String s = "UPDATE employees " +
                        "set last_name='Delgado Ortiz' " +
                        "WHERE first_name = 'Viviana'";
                int rowsAffected = myStatement.executeUpdate(s);
                if (rowsAffected > 0) {
                    System.out.println("An employee has been updated");
                }

                myResultSet = myStatement.executeQuery("SELECT * FROM employees ORDER BY id ASC");
                while (myResultSet.next()) {
                    System.out.println(
                            myResultSet.getString("first_name") + " " +
                                    myResultSet.getString("last_name")
                    );
                }
                return;
            }
            if (example == Example.CASE_4) {
                int employeeId = 1;
                myPreparedStatement = myConnection.prepareStatement("DELETE FROM employees WHERE id=?");
                myPreparedStatement.setInt(1, employeeId);
                int rowsAffected = myPreparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Employee " + employeeId + " has been deleted");
                }
                myStatement = myConnection.createStatement();
                myResultSet = myStatement.executeQuery("SELECT * FROM employees ORDER BY id ASC");
                while (myResultSet.next()) {
                    System.out.println(
                            myResultSet.getString("id") + ") " +
                                    myResultSet.getString("first_name") + " " +
                                    myResultSet.getString("last_name")
                    );
                }
            }
        } catch (SQLException e) {
            System.out.println("Something went wrong");
            e.printStackTrace();
        } finally {
            if (myResultSet != null) {
                myResultSet.close();
            }

            if (myStatement != null) {
                myStatement.close();
            }

            if (myConnection != null) {
                myConnection.close();
            }
        }
    }
}