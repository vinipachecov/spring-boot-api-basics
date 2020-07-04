package com.mobileapi.mobileapi.service;

import java.util.ArrayList;
import java.util.List;

import com.mobileapi.mobileapi.shared.dto.AddressDto;
import com.mobileapi.mobileapi.ui.io.entity.UserEntity;
import com.mobileapi.mobileapi.ui.io.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;

public interface AddressService {

    public List<AddressDto> getAddresses(String userId);
}