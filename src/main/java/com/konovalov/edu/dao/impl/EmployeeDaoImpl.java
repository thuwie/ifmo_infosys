package com.konovalov.edu.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.konovalov.edu.dao.Dao;
import com.konovalov.edu.dao.EmployeeDao;
import com.konovalov.edu.entity.Employee;

@Repository
public class EmployeeDaoImpl extends Dao implements EmployeeDao {

    public List<Employee> getAllEmployees() {
        getCurrentSession().beginTransaction();
        List<Employee> employees = (List<Employee>) getCurrentSession().createCriteria(Employee.class).list();
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
        return employees;
    }

    public Employee getEmployeeById(int employeeId) {
        getCurrentSession().beginTransaction();
        Employee employee = getCurrentSession().get(Employee.class, employeeId);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
        return employee;
    }

    public void addEmployee(Employee employee) {
        getCurrentSession().beginTransaction();
        getCurrentSession().save(employee);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public void updateEmployee(Employee employee) {
        getCurrentSession().beginTransaction();
        getCurrentSession().update(employee);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public void deleteEmployee(int employeeId) {
        Employee employeeById = getEmployeeById(employeeId);
        getCurrentSession().beginTransaction();
        getCurrentSession().delete(employeeById);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }
    
    public boolean isEmployeeExists(Employee employee) {
        getCurrentSession().beginTransaction();
        boolean isEmployeeExists = getCurrentSession().contains(employee);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
        return isEmployeeExists;
    }
}
