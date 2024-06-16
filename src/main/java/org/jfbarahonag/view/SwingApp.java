package org.jfbarahonag.view;

import org.jfbarahonag.model.Employee;
import org.jfbarahonag.repository.EmployeeRepository;
import org.jfbarahonag.repository.IRepository;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class SwingApp extends JFrame {
    private final IRepository<Employee> employeeRepository;
    private final JTable employeeTable;

    public SwingApp() {
        // Setup window
        setTitle("Employee manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 230);

        // Create a table to show up the employees
        employeeTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(employeeTable);
        add(scrollPane, BorderLayout.CENTER);

        // Create buttons for actions
        JButton addButton = new JButton("Add");
        JButton updateButton = new JButton("Update");
        JButton deleteButton = new JButton("Delete");

        // Set up the button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Styling the buttons
        addButton.setBackground(new Color(46, 204, 113));
        addButton.setForeground(Color.WHITE);
        addButton.setFocusPainted(false);

        updateButton.setBackground(new Color(52, 152, 219));
        updateButton.setForeground(Color.WHITE);
        updateButton.setFocusPainted(false);

        deleteButton.setBackground(new Color(231, 76, 60));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFocusPainted(false);

        // create Repository object to access to the database
        employeeRepository = new EmployeeRepository();

        // first load employees
        refreshEmployeesTable();

        // Add event listener to the buttons
        addButton.addActionListener(e -> {
            try {
                addNewEmployee();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });

        updateButton.addActionListener(e -> updateEmployee());
        deleteButton.addActionListener(e -> deleteEmployee());
    }

    private void refreshEmployeesTable() {
        try {
            List<Employee> employees = employeeRepository.findAll();

            // Create data table
            DefaultTableModel model = new DefaultTableModel();
            model.addColumn("Id");
            model.addColumn("First Name");
            model.addColumn("Last Name");

            for (Employee employee : employees) {
                Object[] rowData = {
                        employee.getId(),
                        employee.getFirst_name(),
                        employee.getLast_name()
                };
                model.addRow(rowData);
            }

            // update the model with the updated data
            employeeTable.setModel(model);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error getting the employees data");
        }
    }

    private void addNewEmployee() throws SQLException {
        // form to add a new employee
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();

        Object[] fields = {
                "Nombre", firstNameField,
                "Apellidos", lastNameField
        };

        int result = JOptionPane.showConfirmDialog(
                this,
                fields,
                "Add a new employee",
                JOptionPane.OK_CANCEL_OPTION
        );

        if (result == JOptionPane.OK_OPTION) {
            // Create an instance of an employee
            Employee newEmployee = new Employee(
                    firstNameField.getText(),
                    lastNameField.getText()
            );

            employeeRepository.save(newEmployee);

            refreshEmployeesTable();

            JOptionPane.showMessageDialog(
                    this,
                    "Employee added successfully",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }

    }

    private void updateEmployee() {
        // get the id of the employee
        String employeeIdStr = JOptionPane.showInputDialog(
                this,
                "Type the id of the employee will be updated",
                "Update employee",
                JOptionPane.QUESTION_MESSAGE
        );
        if (employeeIdStr == null) return;

        try {
            int employeeId = Integer.parseInt(employeeIdStr);

            Employee employee = employeeRepository.getById(employeeId);

            if (employee == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "No employee found with ID: " + employeeId,
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            JTextField firstNameField = new JTextField(employee.getFirst_name());
            JTextField lastNameField = new JTextField(employee.getLast_name());

            Object[] fields = {
                    "First Name:", firstNameField,
                    "Last Name:", lastNameField
            };

            int confirmResult = JOptionPane.showConfirmDialog(
                    this,
                    fields,
                    "Update employee",
                    JOptionPane.OK_CANCEL_OPTION
            );

            if (confirmResult != JOptionPane.OK_OPTION) {
                return;
            }

            employee.setFirst_name(firstNameField.getText());
            employee.setLast_name(lastNameField.getText());

            employeeRepository.update(employeeId, employee);

            refreshEmployeesTable();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid employee id",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void deleteEmployee() {
        // get the id of the employee
        String employeeIdStr = JOptionPane.showInputDialog(
                this,
                "Type the id of the employee will be deleted",
                "Update employee",
                JOptionPane.QUESTION_MESSAGE
        );
        if (employeeIdStr == null) return;

        try {
          int employeeId = Integer.parseInt(employeeIdStr);
          int confirmAction = JOptionPane.showConfirmDialog(
                  this,
                  "Are you sure of this action?",
                  "Confirm employee deletion",
                  JOptionPane.YES_NO_OPTION
          );

          if (confirmAction == JOptionPane.NO_OPTION) return;

          int result = employeeRepository.delete(employeeId);

          // something got wrong
          if (result != employeeId) {
              if (result == 0) {
                  JOptionPane.showMessageDialog(
                          this,
                          "Employee not found.",
                          "Error",
                          JOptionPane.ERROR_MESSAGE
                  );
                  return;
              }
              JOptionPane.showMessageDialog(
                      this,
                      "Error deleting employee. Code [" + result + "]",
                      "Error",
                      JOptionPane.ERROR_MESSAGE
              );
              return;
          }

          refreshEmployeesTable();

          JOptionPane.showMessageDialog(
                  this,
                  "Employee successfully deleted",
                  "Success",
                  JOptionPane.INFORMATION_MESSAGE
          );

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Invalid employee id",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
