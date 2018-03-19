package com.smartparking.security;

import com.smartparking.entity.Client;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

@Component
public interface TokenHandler {
    Optional<UserDetails> parseUserFromToken(String token);

    String createTokenForUser(Client client);

    Boolean isTokenExpired(String token);

    Date getCreatedDateFromToken(String token);
}
