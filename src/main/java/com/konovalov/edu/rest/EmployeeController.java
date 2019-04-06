package com.konovalov.edu.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.konovalov.edu.dao.EmployeeDao;
import com.konovalov.edu.entity.Employee;

@RestController
public class EmployeeController {
    
    private EmployeeDao employeeDao;
    
    @Autowired
    public EmployeeController(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }
    
    @CrossOrigin
    @GetMapping(value = "/emp/all")
    @ResponseBody
    public ResponseEntity<List<Employee>> getAllEmployee() {
        List<Employee> allEmployees = employeeDao.getAllEmployees();
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }
    
    @CrossOrigin
    @PostMapping(value = "/emp")
    @ResponseBody
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        employeeDao.addEmployee(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
}
