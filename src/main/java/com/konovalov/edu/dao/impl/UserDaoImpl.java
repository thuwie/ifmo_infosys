package com.konovalov.edu.dao.impl;

import com.konovalov.edu.dao.Dao;
import com.konovalov.edu.dao.UserDao;
import com.konovalov.edu.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDaoImpl extends Dao implements UserDao {

    public List<User> getAllUsers() {
        getCurrentSession().beginTransaction();
        List<User> users = (List<User>) getCurrentSession().createCriteria(User.class).list();
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();

        return users;
    }

    public User getUserById(int userId) {
        getCurrentSession().beginTransaction();
        User user = getCurrentSession().get(User.class, userId);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();

        return user;
    }

    public void addUser(User user) {
        getCurrentSession().beginTransaction();
        getCurrentSession().save(user);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public void updateUser(User user) {
        getCurrentSession().beginTransaction();
        getCurrentSession().update(user);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public void deleteUser(int userId) {
        getCurrentSession().beginTransaction();
        getCurrentSession().delete(getUserById(userId));
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }

    public boolean isUserExists(User user) {
        getCurrentSession().beginTransaction();
        boolean isUserExists = getCurrentSession().contains(user);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();

        return isUserExists;
    }
}
