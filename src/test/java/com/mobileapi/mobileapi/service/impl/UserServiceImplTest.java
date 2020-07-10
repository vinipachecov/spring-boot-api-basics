package com.mobileapi.mobileapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import com.mobileapi.mobileapi.shared.dto.UserDto;
import com.mobileapi.mobileapi.ui.io.entity.UserEntity;
import com.mobileapi.mobileapi.ui.io.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

class UserServiceImplTest {

    // injects dependencies to our SUT class
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepositor;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1l);

        userEntity.setFirstName("valid_first_name");
        userEntity.setLastName("valid_last_name");
        userEntity.setUserId("valid_user_id");
        userEntity.setEncryptedPassword("valid_encrypted_password");

        when(userRepositor.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.getUser("valid_user_email");
        assertNotNull(userDto);
        assertEquals("valid_first_name", userDto.getFirstName());
    }

    @Test
    final void testGetUser_UsernamenotFoundException() {
        when(userRepositor.findByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.getUser("not_valid_email");
        });
    }

}
