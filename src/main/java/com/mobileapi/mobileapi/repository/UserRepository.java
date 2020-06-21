package com.mobileapi.mobileapi.repository;

import com.mobileapi.mobileapi.ui.io.entity.UserEntity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {    
    UserEntity findByEmail(String email);
}