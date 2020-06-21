package com.mobileapi.mobileapi.service;

import com.mobileapi.mobileapi.shared.dto.UserDto;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto ser);
}