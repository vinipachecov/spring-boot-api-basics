package com.mobileapi.mobileapi.service;

import java.util.List;
import com.mobileapi.mobileapi.shared.dto.AddressDto;

public interface AddressService {

    public List<AddressDto> getAddresses(String userId);

    public AddressDto getAddressById(String adressId);
}