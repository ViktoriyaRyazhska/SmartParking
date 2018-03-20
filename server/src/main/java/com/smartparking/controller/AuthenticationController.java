package com.smartparking.controller;

import com.smartparking.dto.AuthToken;
import com.smartparking.dto.LoginClient;
import com.smartparking.entity.Client;
import com.smartparking.security.tokens.TokenUtil;
import com.smartparking.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    private ClientService clientService;

    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody LoginClient loginClient) throws AuthenticationException {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginClient.getEmail(),
                        loginClient.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final Client client = clientService.findOne(loginClient.getEmail());
        final String token = tokenUtil.generateToken(client);
        return ResponseEntity.ok(new AuthToken(token));
    }

}
