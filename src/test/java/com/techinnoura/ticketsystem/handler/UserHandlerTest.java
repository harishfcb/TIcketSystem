package com.techinnoura.ticketsystem.handler;

import com.techinnoura.ticketsystem.CommonUtils.CommonUtils;
import com.techinnoura.ticketsystem.exception.UserException;
import com.techinnoura.ticketsystem.model.User;
import com.techinnoura.ticketsystem.repository.RoleRepository;
import com.techinnoura.ticketsystem.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class UserHandlerTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;



    @InjectMocks
    private UserHandler userHandler;


    @Test
    void createUser() {

        User user = new User();
        user.setEmailId("Test@example.com");
        user.setRoleId("1");
        user.setUserName("existing");
        user.setManagerId("2");

        /**
         * Negative Case : User
         */

        when(userRepository.findByEmailId("Test@example.com")).thenReturn(Optional.of(user));

        UserException userException = assertThrows(UserException.class, () -> userHandler.createUser(user),
                "Expected createUser to throw UserException but it didn't.");

        verify(userRepository, never()).save(any(User.class));

        assertEquals("User with this Email Already exists", userException.getMessage());

        log.info("Negative Test Case passed successfully!");

        /**
         * Positive Case: User
         */
        when(userRepository.findByEmailId("Test@example.com")).thenReturn(Optional.empty());

        assertDoesNotThrow(() -> userHandler.createUser(user),
                "Expected createUser to not throw any exception, but it did.");

        verify(userRepository, times(1)).save(user);

        log.info("Positive Testcase passed successfully!");



    }

}