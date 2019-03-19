package com.konovalov.edu.dao.impl;

import com.konovalov.edu.dao.Dao;
import com.konovalov.edu.dao.UserDao;
import com.konovalov.edu.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends Dao implements UserDao {

    private static final String EMPLOYEE = "employee";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String USER_ID = "userId";

    public List<User> getAllUsers() {
        return (List<User>) getCurrentSession().createCriteria(User.class).list();
    }

    public User getUserById(int userId) {
        return getCurrentSession().get(User.class, userId);
    }

    public void addUser(User user) {
        getCurrentSession().save(user);
    }

    public void updateUser(User user) {
        getCurrentSession().update(user);
    }

    public void deleteUser(int userId) {
        getCurrentSession().delete(getUserById(userId));
    }

    public boolean isUserExists(String password, Integer employeeId, String username) {
        User user = new User();
        user.setUsername(username);
        user.setEmployeeId(employeeId);
        user.setPassword(password);
        return getCurrentSession().contains(user);
    }
}
