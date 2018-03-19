package com.smartparking.security;

import com.smartparking.entity.Client;
import com.smartparking.repository.ClientRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class TokenHandlerImpl implements TokenHandler {
    private static final int MILLISECONDS_TO_SECONDS = 1000;
    private static final String CLAIM_KEY = "authorities";
    private final String secretKey;
    private final ClientRepository clientRepository;

    @Value("${app.jwt.expiration_time}")
    private Long expiration;

    @Autowired
    public TokenHandlerImpl(@Value("${app.jwt.secret}") String secretKey, ClientRepository clientRepository) {
        this.secretKey = secretKey;
        this.clientRepository = clientRepository;
    }

    @Override
    public Optional<UserDetails> parseUserFromToken(String token) {
        final String subject = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        final Client client = clientRepository.getOne(Long.valueOf(subject));
        return Optional.ofNullable(client);
    }

    @Override
    public String createTokenForUser(Client client) {
        return Jwts.builder().setClaims(generateClaims(client))
                .setSubject(String.valueOf(client.getId()))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .setExpiration(generateExpirationDate())
                .compact();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * MILLISECONDS_TO_SECONDS);
    }

    @Override
    public Date getCreatedDateFromToken(String token) {
        Date created;
        try {
            created = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getExpiration();
        } catch (NullPointerException e) {
            created = null;
        }
        return created;
    }

    private Map<String, Object> generateClaims (Client client) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY, client.getAuthorities());
        return claims;
    }

    @Override
    public Boolean isTokenExpired(String token) {
        final Date tokenExpiration = getCreatedDateFromToken(token);
        return tokenExpiration.before(new Date());
    }

}
