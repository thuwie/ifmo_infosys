package com.konovalov.edu.dao;

import com.konovalov.edu.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAO {
    public List<User> getAllusers() {
        return null;
    }

    public User getuserById(int userId) {
        return null;
    }

    public void addUser(User user) {

    }

    public void updateUser(User user) {

    }

    public void deleteUser(int userId) {

    }

    public boolean userExists(String firstName, String secondName, String occupation) {
        return false;
    }
}
