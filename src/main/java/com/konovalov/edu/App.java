package com.konovalov.edu;

import com.konovalov.edu.dao.Dao;
import com.konovalov.edu.entity.User;
import org.hibernate.Session;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);

        Session session = Dao.getSessionFactory().openSession();

        session.beginTransaction();

        User user = new User();
        user.setEmployeeId(1);
        user.setPassword("secret");
        user.setUsername("banana");

        session.save(user);
        session.getTransaction().commit();

        session.close();
    }
}