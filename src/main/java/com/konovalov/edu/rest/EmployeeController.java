package com.konovalov.edu.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.konovalov.edu.dao.EmployeeDao;
import com.konovalov.edu.entity.Employee;
import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/emp")
@AllArgsConstructor
public class EmployeeController {
    
    private EmployeeDao employeeDao;
    
    @GetMapping("/get/{employeeId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("employeeId") Integer id) {
        Employee employee = employeeDao.getEmployeeById(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    
    @GetMapping("/all")
    @ResponseBody
    public ResponseEntity<List<Employee>> getAllEmployee() {
        List<Employee> allEmployees = employeeDao.getAllEmployees();
        return new ResponseEntity<>(allEmployees, HttpStatus.OK);
    }
    
    @PostMapping("/addEmp")
    public ResponseEntity<Employee> addEmployee(@RequestBody Employee employee) {
        employeeDao.addEmployee(employee);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }
    
    @PostMapping("/updateEmp")
    public ResponseEntity<Employee> updateEmployee(@RequestBody Employee employee) {
        employeeDao.updateEmployee(employee);
        Employee employeeById = employeeDao.getEmployeeById(employee.getEmployeeId());
        return new ResponseEntity<>(employeeById, HttpStatus.OK);
    }
    
    @DeleteMapping("delete/{employeeId}")
    public HttpStatus deleteEmployee(@PathVariable("employeeId") Integer id) {
        employeeDao.deleteEmployee(id);
        return HttpStatus.OK;
    }
}
