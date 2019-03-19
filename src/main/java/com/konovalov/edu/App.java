package com.konovalov.edu;

import com.konovalov.edu.dao.UserDao;
import com.konovalov.edu.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    @Autowired
    private static UserDao userDao;

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

        User user = new User("banana", "secrethaha", 1);
        userDao.addUser(user);
    }
}