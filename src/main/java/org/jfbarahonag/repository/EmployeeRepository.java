package org.jfbarahonag.repository;

import org.jfbarahonag.model.Employee;
import org.jfbarahonag.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepository implements IRepository<Employee> {


    private static Employee getEmployeeData(ResultSet response) throws SQLException {
        return new Employee(
                response.getInt("id"),
                response.getString("first_name"),
                response.getString("last_name"),
                response.getString("document")
        );
    }

    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getConnection();
    }

    @Override
    public List<Employee> findAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();
        try(Connection connection = getConnection();
            Statement statement = connection.createStatement();
            ResultSet response = statement.executeQuery("SELECT id, first_name, last_name, document FROM employees")
        ){
            while (response.next()) {
                Employee employee = getEmployeeData(response);
                employees.add(employee);
            }
        }
        return employees;
    }

    @Override
    public Employee getById(Integer id) throws SQLException {
        Employee employee = null;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM employees WHERE id=?")
        ){
            statement.setInt(1, id);
            try(ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    employee = getEmployeeData(result);
                }
            }
        }
        return employee;
    }

    @Override
    public boolean save(Employee record) throws SQLException {
        String sentence = "INSERT INTO employees (first_name, last_name, document) VALUES (?,?,?)";
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sentence)) {
            statement.setString(1, record.getFirst_name());
            statement.setString(2, record.getLast_name());
            statement.setString(3, record.getDocument());
            int rows = statement.executeUpdate();
            return rows == 1;
        }
    }

    @Override
    public boolean update(int id, Employee record) throws SQLException {
        String sentence = "UPDATE employees SET first_name=?, last_name=?, document=? WHERE id=?";
        try(Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement(sentence)) {
            statement.setString(1, record.getFirst_name());
            statement.setString(2, record.getLast_name());
            statement.setString(3, record.getDocument());
            statement.setInt(4, id);
            int rows = statement.executeUpdate();
            return rows == 1;
        }
    }

    @Override
    public Integer delete(Integer id) throws SQLException {
        String sentence = "DELETE FROM employees WHERE id=?";
        int rows = 0;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sentence)) {
            statement.setInt(1, id);
            rows = statement.executeUpdate();
        }
        return rows != 0 ? id : rows;
    }
}
