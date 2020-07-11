package com.mobileapi.mobileapi.ui.io.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity(name = "password_reset_tokens")
public class PasswordResetTokenEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private long id;

    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    @OneToOne
    @JoinColumn(name = "users_id")
    private UserEntity userDetails;

    /**
     * @return long return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return String return the token
     */
    public String getToken() {
        return token;
    }

    /**
     * @return UserEntity return the userDetails
     */
    public UserEntity getUserDetails() {
        return userDetails;
    }

    /**
     * @param userDetails the userDetails to set
     */
    public void setUserDetails(UserEntity userDetails) {
        this.userDetails = userDetails;
    }

}