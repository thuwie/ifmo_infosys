package com.konovalov.edu.rest;

import com.konovalov.edu.dao.UserDao;
import com.konovalov.edu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    private final UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @GetMapping(value = "/user/get/{userId}")
    @ResponseBody
    public ResponseEntity<User> getUserById(@PathVariable("userId") Integer id) {

        User user = userDao.getUserById(id);

        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}
