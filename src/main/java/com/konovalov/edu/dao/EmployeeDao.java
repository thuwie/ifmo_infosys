package com.konovalov.edu.dao;

import com.konovalov.edu.entity.Employee;

import java.util.List;

public interface EmployeeDao {

    List<Employee> getAllEmployees();

    Employee getEmployeeById(int employeeId);

    void addEmployee(Employee employee);

    void updateEmployee(Employee employee);

    void deleteEmployee(int employeeId);

    boolean isEmployeeExists(Employee employee);
}
