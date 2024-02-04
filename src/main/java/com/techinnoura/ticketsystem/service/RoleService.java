package com.techinnoura.ticketsystem.service;

import com.techinnoura.ticketsystem.handler.RoleHandler;
import com.techinnoura.ticketsystem.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleService {

    @Autowired
    RoleHandler roleHandler;

    public Role creatRole(Role role){
        return roleHandler.creatRole(role);
    }
}
