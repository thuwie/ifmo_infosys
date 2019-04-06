package com.konovalov.edu;

import com.konovalov.edu.dao.Dao;
import com.konovalov.edu.entity.User;
import com.konovalov.edu.ProcessDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;


@SpringBootApplication
public class App extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

    }
}


