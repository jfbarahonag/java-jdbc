package org.jfbarahonag;

import org.jfbarahonag.model.Employee;
import org.jfbarahonag.repository.EmployeeRepository;
import org.jfbarahonag.repository.IRepository;
import org.jfbarahonag.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try(Connection connection = DatabaseConnection.getInstance()) {
            if (connection.getAutoCommit()) {
                connection.setAutoCommit(false);
            }

            try {
                IRepository<Employee> employeeRepository = new EmployeeRepository(connection);
                System.out.println("--- Add new employee ---");
                // Adding employee 1
                Employee e = new Employee(
                        "Carlangas",
                        "Lanas Rosas",
                        "1234567890"
                );
                boolean done = employeeRepository.save(e);
                String msg = done ? "Employee created" : "Employee couldn't be created";
                System.out.println("--- "+msg+" ---");
                // Adding employee 2
                Employee e2 = new Employee(
                        "Papilla",
                        "Rosales Candida",
                        "1234567890"
                );
                done = employeeRepository.save(e2);
                connection.commit();
                msg = done ? "Employee created" : "Employee couldn't be created";
                System.out.println("--- "+msg+" ---");
            } catch (SQLException ex) {
                connection.rollback();
                System.out.println("--- Rollback executed ---");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}