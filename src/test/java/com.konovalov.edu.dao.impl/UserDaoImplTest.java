package com.konovalov.edu.dao.impl;

import com.konovalov.edu.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserDaoImplTest {

    @InjectMocks
    private UserDaoImpl dao;

    @Test
    public void addUser() {
        User user = new User("banana", "secrethaha", 1);
        dao.addUser(user);
    }
}
