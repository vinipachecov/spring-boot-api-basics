package com.mobileapi.mobileapi.service;

import java.util.List;

import com.mobileapi.mobileapi.shared.dto.UserDto;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto ser);

    UserDto getUser(String email);

    List<UserDto> getUsers(int page, int limit);

    UserDto getUserByUserId(String userId);

    UserDto updateUser(String id, UserDto userDto);

    void deleteUser(String id);

    boolean verifyEmailToken(String token);
}