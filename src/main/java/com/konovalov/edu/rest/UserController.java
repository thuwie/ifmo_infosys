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

import com.konovalov.edu.dao.UserDao;
import com.konovalov.edu.entity.User;
import com.konovalov.edu.entity.combinedentity.UserEmployee;
import lombok.AllArgsConstructor;

@CrossOrigin
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserDao userDao;
    
    @GetMapping(value = "/get/{userId}")
    @ResponseBody
    public ResponseEntity<UserEmployee> getUserById(@PathVariable("userId") Integer id) {
        UserEmployee userWithEmpById = userDao.getUserWithEmpById(id);
        return new ResponseEntity<>(userWithEmpById, HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public ResponseEntity<List<UserEmployee>> getUsers() {
        List<UserEmployee> allUsersWithEmpById = userDao.getAllUsersWithEmpById();
        return new ResponseEntity<>(allUsersWithEmpById, HttpStatus.OK);
    }

    @PostMapping(value = "/addUser")
    @ResponseBody
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userDao.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
    @PostMapping(value = "/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userDao.updateUser(user);
        User updatedUser = userDao.getUserById(user.getUserId());
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    
    @DeleteMapping("delete/{userId}")
    public HttpStatus deleteUser(@PathVariable("userId") Integer id) {
        userDao.deleteUser(id);
        return HttpStatus.OK;
    }
}
