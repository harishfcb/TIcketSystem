package com.techinnoura.ticketsystem.handler;


import com.techinnoura.ticketsystem.CommonUtils.CommonUtils;
import com.techinnoura.ticketsystem.exception.UserException;
import com.techinnoura.ticketsystem.model.RoleType;
import com.techinnoura.ticketsystem.model.User;
import com.techinnoura.ticketsystem.repository.RoleRepository;
import com.techinnoura.ticketsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
            throw new UserException("User with this Email Already exists", HttpStatus.BAD_REQUEST);
        }

        if (RoleType.CODER.equals(commonUtils.roleTypeFromRoleId(user.getRoleId())) && (user.getManagerId() == null || user.getManagerId().isEmpty())) {
            throw new UserException("Coders must have a manager. Please provide a valid managerId ",HttpStatus.BAD_REQUEST);
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
