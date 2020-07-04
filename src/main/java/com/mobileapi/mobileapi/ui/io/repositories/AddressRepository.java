package com.mobileapi.mobileapi.ui.io.repositories;

import java.util.List;

import com.mobileapi.mobileapi.ui.io.entity.AddressEntity;
import com.mobileapi.mobileapi.ui.io.entity.UserEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<AddressEntity, Long> {
    List<AddressEntity> findAllByUserDetails(UserEntity userEntity);

    AddressEntity findByAddressId(String addressId);
}