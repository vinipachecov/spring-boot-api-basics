package com.mobileapi.mobileapi.ui.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.mobileapi.mobileapi.service.UserService;
import com.mobileapi.mobileapi.service.impl.UserServiceImpl;
import com.mobileapi.mobileapi.shared.dto.AddressDto;
import com.mobileapi.mobileapi.shared.dto.UserDto;
import com.mobileapi.mobileapi.ui.UserController;
import com.mobileapi.mobileapi.ui.model.response.UserRest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    UserDto userDto;

    final String USER_ID = "public_user_id";

    public UserDto makeUserDto() {
        UserDto userDto = new UserDto();
        userDto.setAddresses(makeAddressesDto());
        userDto.setEmailVerificationStatus(Boolean.FALSE);
        userDto.setEmailVerificationStatus(Boolean.FALSE);
        userDto.setUserId(USER_ID);
        userDto.setFirstName("first_name");
        userDto.setLastName("last_name");
        userDto.setPassword("password");
        userDto.setEmail("email@email.com");
        return userDto;
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

    @BeforeEach
    void setUp() throws Exception {
        // enable mock injections
        MockitoAnnotations.initMocks(this);
        this.userDto = makeUserDto();
    }

    @Test
    final void testGetUser() {
        // mock return of getUserByUserId
        when(userService.getUserByUserId(anyString())).thenReturn(userDto);

        UserRest userRest = userController.getUser(USER_ID);
        assertNotNull(userRest);
        assertEquals(USER_ID, userRest.getUserId());
        assertEquals(userDto.getFirstName(), userRest.getFirstName());
        assertEquals(userDto.getLastName(), userRest.getLastName());
        assertEquals(userDto.getAddresses().size(), userRest.getAddresses().size());

    }
}