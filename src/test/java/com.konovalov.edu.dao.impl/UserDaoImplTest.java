package com.konovalov.edu.dao.impl;

import com.konovalov.edu.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

public class UserDaoImplTest {

    private static SessionFactory sessionFactory;
    private static final String EMPLOYEE = "employee";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private UserDaoImpl dao = new UserDaoImpl();

    @Before
    public void before() {
        sessionFactory = buildSessionFactory();
    }

    private static SessionFactory buildSessionFactory() {

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure()
                .build();
        try {
            sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);

            throw new ExceptionInInitializerError("Initial SessionFactory failed" + e);
        }
        return sessionFactory;
    }

    private static Session getCurrentSession() {
        if (sessionFactory.getCurrentSession() == null) {
            return sessionFactory.openSession();
        }
        return sessionFactory.getCurrentSession();
    }

    @Test
    public void addUser() {
        User user = new User("banana", "secrethaha", 1);
        getCurrentSession().createSQLQuery(
                "INSERT INTO user (employee, username, password) " +
                        "VALUES (:" + EMPLOYEE + ", :" + USERNAME + ", :" + PASSWORD + ")")
                .setParameter(EMPLOYEE, user.getEmployeeId())
                .setParameter(USERNAME, user.getUsername())
                .setParameter(PASSWORD, user.getPassword())
                .executeUpdate();

        // OR:

        dao.addUser(user);
    }
}
