package com.mobileapi.mobileapi.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.management.modelmbean.ModelMBean;

import com.mobileapi.mobileapi.exceptions.UserServiceException;
import com.mobileapi.mobileapi.service.AddressService;
import com.mobileapi.mobileapi.service.UserService;
import com.mobileapi.mobileapi.shared.Utils;
import com.mobileapi.mobileapi.shared.dto.AddressDto;
import com.mobileapi.mobileapi.shared.dto.UserDto;
import com.mobileapi.mobileapi.ui.io.entity.AddressEntity;
import com.mobileapi.mobileapi.ui.io.entity.UserEntity;
import com.mobileapi.mobileapi.ui.io.repositories.UserRepository;
import com.mobileapi.mobileapi.ui.model.response.ErrorMessages;

import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AddressRepository addressRepository;

    @Override
    public List<AddressDto> getAddresses(String userId) {
        List<AddressDto> returnValue = new ArrayList<>();
        ModelMapper modelMapper = new ModelMapper();

        UserEntity userEntity = userRepository.findByUserId(userId);
        if (userEntity == null)
            return returnValue;

        Iterable<AddressEntity> addresses = addressRepository.findAllByUserDetails(userEntity);
        for (AddressEntity addressEntity : addresses) {
            returnValue.add(modelMapper.map(addressEntity, AddressDto.class));
        }

        return returnValue;
    }

}