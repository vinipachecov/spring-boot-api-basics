package com.mobileapi.mobileapi.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.mobileapi.mobileapi.exceptions.UserServiceException;
import com.mobileapi.mobileapi.shared.AmazonSES;
import com.mobileapi.mobileapi.shared.Utils;
import com.mobileapi.mobileapi.shared.dto.AddressDto;
import com.mobileapi.mobileapi.shared.dto.UserDto;
import com.mobileapi.mobileapi.ui.io.entity.AddressEntity;
import com.mobileapi.mobileapi.ui.io.entity.UserEntity;
import com.mobileapi.mobileapi.ui.io.repositories.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserServiceImplTest {

    // injects dependencies to our SUT class
    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    AmazonSES amazonSES;

    String userId = "valid_user_id";

    String encryptedPassword = "valid_encrypted_password";

    UserEntity userEntity;

    public UserDto makeUserDto() {
        UserDto userDto = new UserDto();
        userDto.setAddresses(makeAddressesDto());
        userDto.setFirstName("first_name");
        userDto.setLastName("last_name");
        userDto.setPassword("password");
        userDto.setEmail("email@email.com");
        return userDto;
    }

    public void makeUserEntity() {
        userEntity = new UserEntity();
        userEntity.setId(1l);
        userEntity.setFirstName("valid_first_name");
        userEntity.setLastName("valid_last_name");
        userEntity.setUserId(userId);
        userEntity.setEncryptedPassword("valid_encrypted_password");
        userEntity.setEmail("valid_email");
        userEntity.setEmailVerificationToken("valid_email_verification_token");
        userEntity.setAddresses(getAddressesEntity());
    }

    private List<AddressDto> makeAddressesDto() {
        AddressDto addressDto = new AddressDto();
        addressDto.setType("shipping");
        addressDto.setCity("city1");
        addressDto.setCountry("country1");
        addressDto.setPostalCode("postalcode1");
        addressDto.setStreetName("street1");

        AddressDto billingAddressDto = new AddressDto();
        addressDto.setType("billing");
        addressDto.setCity("city1");
        addressDto.setCountry("country2");
        addressDto.setPostalCode("postalcode2");
        addressDto.setStreetName("street2");
        List<AddressDto> addresses = new ArrayList<>();
        addresses.add(addressDto);
        addresses.add(billingAddressDto);
        return addresses;
    }

    private List<AddressEntity> getAddressesEntity() {
        List<AddressDto> addressDtos = makeAddressesDto();
        Type listType = new TypeToken<List<AddressEntity>>() {
        }.getType();
        return new ModelMapper().map(addressDtos, listType);
    }

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.makeUserEntity();
    }

    @Test
    void testGetUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);

        UserDto userDto = userService.getUser("valid_user_email");
        assertNotNull(userDto);
        assertEquals("valid_first_name", userDto.getFirstName());
    }

    @Test
    final void testGetUser_UsernamenotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);

        assertThrows(UsernameNotFoundException.class, () -> {
            userService.getUser("not_valid_email");
        });
    }

    @Test
    final void testCreateUser_CreateUserServiceException() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
        UserDto userDto = makeUserDto();
        assertThrows(UserServiceException.class, () -> {
            userService.createUser(userDto);
        });
    }
    

    @Test
    final void testCreateUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateAddressId(anyInt())).thenReturn("valid_address_id");
        when(utils.generateUserId(anyInt())).thenReturn(userId);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        Mockito.doNothing().when(amazonSES).verifyEmail(any(UserDto.class));

        UserDto userDto = makeUserDto();        

        UserDto storedUserDetails = userService.createUser(userDto);
        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
        assertNotNull(storedUserDetails.getUserId());
        assertEquals(storedUserDetails.getAddresses().size(), userEntity.getAddresses().size());
        verify(utils, times(2)).generateAddressId(30);
        verify(bCryptPasswordEncoder, times(1)).encode(userDto.getPassword());
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

}
