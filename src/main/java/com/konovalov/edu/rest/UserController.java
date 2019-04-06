package com.konovalov.edu.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.konovalov.edu.dao.UserDao;
import com.konovalov.edu.entity.User;

@RestController
public class UserController {

    private final UserDao userDao;

    public static boolean isNullOrEmpty(String str) {
        return (str != null && !str.isEmpty());
    }

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    // TODO(ipolyakov): add demo task initiator here (POST)

    @CrossOrigin
    @GetMapping(value = "/user/get/{userId}")
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer id) {

        User user = userDao.getUserById(id);
        if(user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);

    }

    @CrossOrigin
    @GetMapping(value = "/user/all")
    @ResponseBody
    public ResponseEntity<List<User>> getUsers() {

        List<User> users = userDao.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);

    }

    @CrossOrigin
    @PostMapping(value = "/authenticate")
    @ResponseBody
    public ResponseEntity<String> autheticate() {

        String hello = "std::cout << \"Hallo, Leute!\"";

        return new ResponseEntity<>(hello, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping(value = "/updateUser")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userDao.updateUser(user);

        User updatedUser = userDao.getUserById(user.getUserId());

        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
    
    @CrossOrigin
    @PostMapping(value = "/user")
    @ResponseBody
    public ResponseEntity<User> addEmployee(@RequestBody User user) {
        userDao.addUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    
}
