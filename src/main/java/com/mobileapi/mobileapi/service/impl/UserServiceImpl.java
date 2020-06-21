package com.mobileapi.mobileapi.service.impl;

import java.util.ArrayList;

import com.mobileapi.mobileapi.repository.UserRepository;
import com.mobileapi.mobileapi.service.UserService;
import com.mobileapi.mobileapi.shared.Utils;
import com.mobileapi.mobileapi.shared.dto.UserDto;
import com.mobileapi.mobileapi.ui.io.entity.UserEntity;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDto createUser(UserDto user) {
        UserEntity userEntity = new UserEntity();       

        if (userRepository.findByEmail(user.getEmail()) != null) throw new RuntimeException("Record already exists");
        
        BeanUtils.copyProperties(user, userEntity);        
        userEntity.setUserId(utils.generateUserId(30));
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));        

        UserEntity storedUserDetails = userRepository.save(userEntity);        
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(storedUserDetails, returnValue);
        return returnValue;
    }

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {		
        System.out.println("email recebido = " + email);
        UserEntity userEntity =  userRepository.findByEmail(email);
        if (userEntity == null) throw new UsernameNotFoundException(email);
        
        return new User(
            userEntity.getEmail(), 
            userEntity.getEncryptedPassword(),
            new ArrayList<>()
        );
	}
    
}