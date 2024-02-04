package com.techinnoura.ticketsystem.handler;


import com.techinnoura.ticketsystem.commonUtils.CommonUtils;
import com.techinnoura.ticketsystem.model.RoleType;
import com.techinnoura.ticketsystem.model.User;
import com.techinnoura.ticketsystem.repository.RoleRepository;
import com.techinnoura.ticketsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class UserHandler {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    CommonUtils commonUtils;

    public User createUser(User user) throws Exception {
        Optional<User> existingUser = userRepository.findByEmailId(user.getEmailId());

        if(existingUser.isPresent()){
            throw new Exception("User with this Email Already exists");
        }

        if (RoleType.CODER.equals(commonUtils.roleTypeFromRoleId(user.getRoleId())) && (user.getManagerId() == null || user.getManagerId().isEmpty())) {
            throw new Exception("Coders must have a manager. Please provide a valid managerId.");
        }

        if(RoleType.MANAGER.equals(commonUtils.roleTypeFromRoleId(user.getRoleId()))){
            user.setManagerId(UUID.randomUUID().toString());
        }
        return userRepository.save(user);
    }

    public Optional<User> findUserByEmail(String emailId) {
        return userRepository.findByEmailId(emailId);
    }


}
