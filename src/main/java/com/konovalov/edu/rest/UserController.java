package com.konovalov.edu.rest;

import com.konovalov.edu.dao.UserDao;
import com.konovalov.edu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
public class UserController {

    private final UserDao userDao;

    public static boolean isNullOrEmpty(String str) {
        if(str != null && !str.isEmpty())
            return false;
        return true;
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
            return new ResponseEntity<User>(user, HttpStatus.OK);
        else
            return new ResponseEntity<User>(user, HttpStatus.NO_CONTENT);

    }

    @CrossOrigin
    @GetMapping(value = "/user/all")
    @ResponseBody
    public ResponseEntity<List<User>> getUsers() {

        List<User> users = userDao.getAllUsers();

        return new ResponseEntity<List<User>>(users, HttpStatus.OK);

    }

    @CrossOrigin
    @PostMapping(value = "/authenticate")
    @ResponseBody
    public ResponseEntity<String> autheticate() {

        String hello = "std::cout << \"Hallo, Leute!\"";

        return new ResponseEntity<String>(hello, HttpStatus.OK);
    }
}
