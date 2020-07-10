package com.mobileapi.mobileapi.ui.io.repositories;

import com.mobileapi.mobileapi.ui.io.entity.PasswordResetTokenEntity;

import org.springframework.data.repository.CrudRepository;

public interface PasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long> {

}