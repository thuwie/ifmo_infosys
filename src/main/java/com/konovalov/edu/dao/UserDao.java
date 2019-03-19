package com.konovalov.edu.dao;

import com.konovalov.edu.entity.User;

import java.util.List;

public interface UserDao {

    List<User> getAllUsers();

    User getUserById(int userId);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(int userId);

    boolean ifUserExists(String password, Integer employeeId, String username);
}
