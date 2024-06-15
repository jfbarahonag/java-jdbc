package org.jfbarahonag;

import org.jfbarahonag.model.Employee;
import org.jfbarahonag.repository.EmployeeRepository;
import org.jfbarahonag.repository.IRepository;
import org.jfbarahonag.util.DatabaseConnection;

import javax.swing.plaf.nimbus.State;
import java.sql.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws SQLException {

        enum Example {
            CASE_1,
            CASE_2,
            CASE_3,
            CASE_4,
        }

        Example example = Example.CASE_1;

        if (example == Example.CASE_1) {
            try {
                IRepository<Employee> repository = new EmployeeRepository();
                repository.findAll().forEach(System.out::println);
                System.out.println("---------------------------------");
                System.out.println(repository.getById(6));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        if (example == Example.CASE_2) {
            String sqlStatemet = "INSERT INTO employees (first_name, last_name) VALUES (?, ?)";
            try (Connection myConnection = DatabaseConnection.getInstance();
                 PreparedStatement myPreparedStatement = myConnection.prepareStatement(sqlStatemet);
            ) {
                myPreparedStatement.setString(1, "Pepito");
                myPreparedStatement.setString(2, "Perez Sosa");
                int rowsAffected = myPreparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("New employee has been added");
                }
            } catch (SQLException e) {
                System.out.println("Something went wrong");
                e.printStackTrace();
            }
        }

        if (example == Example.CASE_3) {
            try (Connection myConnection = DatabaseConnection.getInstance();
                 Statement myStatement = myConnection.createStatement();
            ) {
                String s = "UPDATE employees " +
                        "set last_name='Delgado Ortiz' " +
                        "WHERE first_name = 'Viviana'";

                int rowsAffected = myStatement.executeUpdate(s);
                if (rowsAffected > 0) {
                    System.out.println("An employee has been updated");
                }


            } catch (SQLException e) {
                System.out.println("Something went wrong");
                e.printStackTrace();
            }

            try (Connection myConnection = DatabaseConnection.getInstance();
                 Statement myStatement = myConnection.createStatement();
                 ResultSet myResultSet = myStatement.executeQuery("SELECT * FROM employees ORDER BY id ASC");
                 ) {
                while (myResultSet.next()) {
                    System.out.println(
                            myResultSet.getString("id") + ") " +
                                    myResultSet.getString("first_name") + " " +
                                    myResultSet.getString("last_name")
                    );
                }
            } catch (SQLException e) {
                System.out.println("Something went wrong");
                e.printStackTrace();
            }
        }

        if (example == Example.CASE_4) {
            try (Connection myConnection = DatabaseConnection.getInstance();
                 PreparedStatement myPreparedStatement = myConnection.prepareStatement("DELETE FROM employees WHERE id=?");
            ) {
                int employeeId = 5;
                myPreparedStatement.setInt(1, employeeId);
                int rowsAffected = myPreparedStatement.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Employee " + employeeId + " has been deleted");
                }
            } catch (SQLException e) {
                System.out.println("Something went wrong");
                e.printStackTrace();
            }

            try (Connection myConnection = DatabaseConnection.getInstance();
                 Statement myStatement = myConnection.createStatement();
                 ResultSet myResultSet = myStatement.executeQuery("SELECT * FROM employees ORDER BY id ASC");
            ) {
                while (myResultSet.next()) {
                    System.out.println(
                            myResultSet.getString("id") + ") " +
                                    myResultSet.getString("first_name") + " " +
                                    myResultSet.getString("last_name")
                    );
                }
            } catch (SQLException e) {
                System.out.println("Something went wrong");
                e.printStackTrace();
            }
        }
    }
}