package com.konovalov.edu.rest;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.konovalov.edu.dao.EmployeeDao;
import com.konovalov.edu.dao.UserDao;
import com.konovalov.edu.entity.Employee;
import com.konovalov.edu.entity.User;
import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserDao userDao;
    private final EmployeeDao employeeDao;
    
    @GetMapping(value = "/get/{userId}")
    @ResponseBody
    public ResponseEntity<Map> getUserById(@PathVariable("userId") Integer id) {
        User user = userDao.getUserById(id);
        LinkedHashMap<String, Object> jsonStructure = new LinkedHashMap<>();
        if(user != null) {
            Employee employee = employeeDao.getEmployeeById(user.getEmployeeId());
            jsonStructure.put("userId", user.getUserId());
            jsonStructure.put("username", user.getUsername());
            jsonStructure.put("roleId", user.getRoleId());
            jsonStructure.put("employee", employee);
            return new ResponseEntity<>(jsonStructure, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(jsonStructure, HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<List<User>> getUsers() {

        List<User> users = userDao.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @PostMapping(value = "/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userDao.updateUser(user);

        User updatedUser = userDao.getUserById(user.getUserId());

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping(value = "/addUser")
    @ResponseBody
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userDao.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
}
