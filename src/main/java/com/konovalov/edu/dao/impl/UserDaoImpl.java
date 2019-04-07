package com.konovalov.edu.dao.impl;

import java.util.List;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import com.konovalov.edu.dao.Dao;
import com.konovalov.edu.dao.UserDao;
import com.konovalov.edu.entity.User;
import com.konovalov.edu.entity.combinedentity.UserEmployee;

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
        User userById = getUserById(userId);
        getCurrentSession().beginTransaction();
        getCurrentSession().delete(userById);
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
    }
    
    public User getUserByName(String username) {
        getCurrentSession().beginTransaction();
        Query query = getCurrentSession().createQuery("from User where username = :name");
        query.setParameter("name", username);
        User user = (User) query.getSingleResult();
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
        return user;
    }
    
    public UserEmployee getUserWithEmpById(Integer id) {
        getCurrentSession().beginTransaction();
        UserEmployee userEmployee = (UserEmployee) getCurrentSession().createQuery(
           "select new com.konovalov.edu.entity.combinedentity.UserEmployee(u.userId, u.username, u.password," +
                      " r.name, e.firstName, e.secondName) " +
                      "from Role r inner join User u on r.roleId = u.roleId " +
                      "inner join Employee e on u.employeeId= e.employeeId " +
                      "where u.userId = :id").setParameter("id", id).getSingleResult();
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
        return userEmployee;
    }
    
    public List<UserEmployee> getAllUsersWithEmpById() {
        getCurrentSession().beginTransaction();
        @SuppressWarnings("unchecked")
        List<UserEmployee> userEmployees = getCurrentSession().createQuery(
                "select new com.konovalov.edu.entity.combinedentity.UserEmployee(u.userId, u.username, u.password," +
                           " r.name, e.firstName, e.secondName) " +
                           "from Role r inner join User u on r.roleId = u.roleId " +
                           "inner join Employee e on u.employeeId= e.employeeId").list();
        getCurrentSession().getTransaction().commit();
        getCurrentSession().close();
        return userEmployees;
    }
}
