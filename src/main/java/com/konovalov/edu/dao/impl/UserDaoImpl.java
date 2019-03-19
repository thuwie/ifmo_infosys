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
        return (List<User>) getCurrentSession().createSQLQuery("SELECT * FROM user")
                .addEntity(User.class)
                .list();
    }

    public User getUserById(int userId) {
        return (User) getCurrentSession().createSQLQuery("SELECT * FROM user WHERE user_id = :" + USER_ID)
                .addEntity(User.class)
                .setParameter(USER_ID, userId)
                .uniqueResult();
    }

    public void addUser(User user) {
        getCurrentSession().createSQLQuery(
                "INSERT INTO user (employee, username, password) " +
                        "VALUES (:" + EMPLOYEE + ", :" + USERNAME + ", :" + PASSWORD + ")")
                .setParameter(EMPLOYEE, user.getEmployeeId())
                .setParameter(USERNAME, user.getUsername())
                .setParameter(PASSWORD, user.getPassword())
                .executeUpdate();

    }

    public void updateUser(User user) {
        getCurrentSession().createSQLQuery(
                "UPDATE user " +
                        "SET employee = :" + EMPLOYEE +
                        ", username = :" + USERNAME +
                        ", password = :" + PASSWORD +
                        "WHERE user_id = :" + USER_ID)
                .setParameter(EMPLOYEE, user.getEmployeeId())
                .setParameter(USERNAME, user.getUsername())
                .setParameter(PASSWORD, user.getPassword())
                .setParameter(USER_ID, user.getUserId())
                .executeUpdate();

    }

    public void deleteUser(int userId) {
        getCurrentSession().createSQLQuery(
                "DELETE FROM user " +
                        "WHERE user_id = :" + USER_ID)
                .setParameter(USER_ID, userId)
                .executeUpdate();
    }

    public boolean ifUserExists(String password, Integer employeeId, String username) {
        User user = (User) getCurrentSession().createSQLQuery("SELECT * FROM user " +
                "WHERE username = :" + USERNAME +
                "AND employee = :" + EMPLOYEE +
                "AND password = :" + PASSWORD)
                .addEntity(User.class)
                .setParameter(EMPLOYEE, employeeId)
                .setParameter(USERNAME, username)
                .setParameter(PASSWORD, password);
        return user != null;
    }
}
