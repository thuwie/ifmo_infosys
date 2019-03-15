package com.konovalov.edu.dao;

import com.konovalov.edu.entity.User;

import java.util.List;

public interface UserDAO {

    List<User> getAllusers();

    User getuserById(int userId);

    void addUser(User user);

    void updateUser(User user);

    void deleteUser(int userId);

    boolean userExists(String firstName, String secondName, String occupation);
}
