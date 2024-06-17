package org.jfbarahonag;

import org.jfbarahonag.model.Employee;
import org.jfbarahonag.repository.EmployeeRepository;
import org.jfbarahonag.repository.IRepository;

import java.sql.SQLException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        try {
            IRepository<Employee> repository = new EmployeeRepository();

            System.out.println("--- Getting all employees---");
            repository.findAll().forEach(System.out::println);

            System.out.println("--- Getting an employee by ID---");
            int employeeId = 3;
            System.out.println(repository.getById(employeeId));
        } catch (SQLException e) {
            System.out.println("SQL Exception");
            e.printStackTrace();
        }
    }
}