package com.smartparking.controller;

import com.smartparking.model.request.SocialSignInRequest;
import com.smartparking.security.exception.*;
import com.smartparking.model.request.RegistrationRequest;
import com.smartparking.model.response.AuthTokenResponse;
import com.smartparking.model.request.LoginRequest;
import com.smartparking.model.response.InfoResponse;
import com.smartparking.security.tokens.TokenPair;
import com.smartparking.service.SecurityService;
import com.smartparking.service.email.EmailService;
import com.smartparking.service.impl.SecurityServiceImpl;
import com.smartparking.security.tokens.TokenUtil;
import com.smartparking.security.utils.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class SecurityController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);
    @Autowired
    private Validator validator;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenUtil tokenUtil;

    @Autowired
    @Qualifier("MyUserDetails")
    private UserDetailsService userService;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    EmailService emailService;

    @PostMapping(value = "/generate-token")
    public ResponseEntity register(@RequestBody LoginRequest loginRequest) throws AuthenticationException {
        final String email;
        final String password;
        try {
            email = validator.validateEmailOnLogin(loginRequest.getEmail());
            password = validator.validatePassword(loginRequest.getPassword());
        } catch (AuthorizationEx e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InfoResponse(e.getMessage()));
        }
        final UserDetails user = userService.loadUserByUsername(email);
        LOGGER.info(email + " = " + user);
        if (user != null && bcryptEncoder.matches(password, user.getPassword())) {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final TokenPair tokenPair = tokenUtil.generateTokenPair(user);
            return ResponseEntity.ok(new AuthTokenResponse(tokenPair.getAccessToken(), tokenPair.getRefreshToken()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InfoResponse("Password is incorrect"));
    }

    @PostMapping("/signup")
    public ResponseEntity saveUser(@RequestBody RegistrationRequest regReq) {
        try {
            securityService.saveClientFromRegistrationRequest(regReq);
        } catch (AuthorizationEx e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InfoResponse(e.getMessage()));
        }
        new Thread(() -> emailService.prepareAndSendWelcomeEmail(regReq.getEmail(), regReq.getFirstname())).start();
        return ResponseEntity.status(HttpStatus.OK).body(new InfoResponse("You are successfull registered"));
    }

    @PostMapping(value = "/social")
    public ResponseEntity socialSignIn(@RequestBody SocialSignInRequest request) {
        UserDetails user = null;
        try {
            user = userService.loadUserByUsername(request.getEmail());
        } catch (Exception e) {
            e.getMessage();
        }
        System.out.println(user);
        if (user == null) {
            System.out.println("Try to register");
            try {
                securityService.saveClientFromSocialSignInRequest(request);
            } catch (AuthorizationEx e) {
                LOGGER.warn(e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InfoResponse(e.getMessage()));
            }
            user = userService.loadUserByUsername(request.getEmail());
        }
        if (user != null) {
            System.out.println(user.getUsername());
            System.out.println(user.getPassword());
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final TokenPair tokenPair = tokenUtil.generateTokenPair(user);
            return ResponseEntity.ok(new AuthTokenResponse(tokenPair.getAccessToken(), tokenPair.getRefreshToken()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new InfoResponse("Sorry, can`t authorize you now."));
    }
}
