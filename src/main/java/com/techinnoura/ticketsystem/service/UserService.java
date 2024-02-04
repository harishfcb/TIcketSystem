package com.techinnoura.ticketsystem.service;

import com.techinnoura.ticketsystem.handler.UserHandler;
import com.techinnoura.ticketsystem.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserHandler userHandler;

    public User createUser(User user) throws Exception {
        return userHandler.createUser(user);
    }
}
