package com.mobileapi.mobileapi.shared;

import java.security.SecureRandom;
import java.util.Date;
import java.util.Random;

import com.mobileapi.mobileapi.security.SecurityConstants;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABET = "0123456789ABCEFGHIJLKMOPQNRSTUVXWLabcdefghijklmnopqrstuvwyxz";
    private final int ITERATIONS = 10000;
    private final int KEY_LENGTH = 256;

    public String generateUserId(int length) {
        return generateRandomString(length);
    }

    public String generateRandomString(int length) {
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    public String generateAddressId(int length) {
        return generateRandomString(length);
    }

    public static boolean hasTokenExpired(String token) {
        boolean returnValue = true;
        try {
            Claims claims = Jwts.parser().setSigningKey(SecurityConstants.getTokenSecret()).parseClaimsJws(token)
                    .getBody();

            Date tokenExpireDate = claims.getExpiration();
            Date todayDate = new Date();

            returnValue = tokenExpireDate.before(todayDate);
        } catch (ExpiredJwtException ex) {
            returnValue = true;
        }
        return returnValue;
    }

    public String generateEmailVerificationToken(String userId) {
        String token = Jwts.builder().setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();
        return token;
    }

    public String generatePasswordResetToken(String userId) {
        String token = Jwts.builder().setSubject(userId)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.getTokenSecret()).compact();
        return token;
    }

}