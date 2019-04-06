package com.konovalov.edu.rest;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.konovalov.edu.dao.UserDao;
import com.konovalov.edu.entity.User;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserDao userDao;

    public static boolean isNullOrEmpty(String str) {
        return (str != null && !str.isEmpty());
    }

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping(value = "/get/{userId}")
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer id) {

        User user = userDao.getUserById(id);
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);

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
