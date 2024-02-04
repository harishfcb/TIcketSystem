package com.techinnoura.ticketsystem.handler;

import com.techinnoura.ticketsystem.model.Role;
import com.techinnoura.ticketsystem.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class RoleHandler {

    @Autowired
    RoleRepository roleRepository;

    public Role creatRole(Role role) {
        role.setRoleId(UUID.randomUUID().toString());
        return roleRepository.save(role);
    }



}
