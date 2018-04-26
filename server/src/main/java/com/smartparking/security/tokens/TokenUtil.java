package com.smartparking.security.tokens;


import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;


@Component
public interface TokenUtil {
    TokenPair generateTokenPair(UserDetails user);

    String getUsernameFromToken(String token);
}
