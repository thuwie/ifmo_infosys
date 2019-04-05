package com.konovalov.edu.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.konovalov.edu.dao.EmployeeDao;
import com.konovalov.edu.entity.Employee;
import com.konovalov.edu.entity.Role;

@RestController
public class EmployeeController {
    
    private EmployeeDao employeeDao;
    
    @Autowired
    public EmployeeController(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
    
    @CrossOrigin
    @GetMapping(value = "/emp/{empID}/{roleID}")
    @ResponseBody
    public ResponseEntity<Employee> addRole(@PathVariable("empID") Integer empID,
                                            @PathVariable("roleID") Integer roleID) {
        Employee employeeById = employeeDao.getEmployeeById(empID);
        employeeById.setRoleId(roleID);
        employeeDao.updateEmployee(employeeById);
        return null;
    }
    
    @CrossOrigin
    @GetMapping(value = "/emp/all")
    @ResponseBody
    public ResponseEntity<List<Employee>> getAllEmployee() {
        List<Employee> allEmployees = employeeDao.getAllEmployees();
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }
    
    @CrossOrigin
    @GetMapping(value = "/emp/{empID}/{roleID}/{firstName}/{secondName}")
    @ResponseBody
    public ResponseEntity<List<Employee>> addEmployee(@PathVariable("empID") Integer empID,
                                                      @PathVariable("roleID") Integer roleID,
                                                      @PathVariable("firstName") String firstName,
                                                      @PathVariable("secondName") String secondName) {
        Employee employee = new Employee();
        employee.setArticleId(empID);
        employee.setRoleId(roleID);
        employee.setFirstName(firstName);
        employee.setSecondName(secondName);
        employeeDao.addEmployee(employee);
        return null;
    }
    
}
