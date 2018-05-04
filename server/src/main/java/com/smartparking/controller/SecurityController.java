package com.smartparking.controller;

import com.smartparking.entity.Client;
import com.smartparking.entity.ConfirmationType;
import com.smartparking.entity.TemporaryDataConfirmation;
import com.smartparking.model.request.LoginRequest;
import com.smartparking.model.request.RegistrationRequest;
import com.smartparking.model.request.SocialSignInRequest;
import com.smartparking.model.response.AuthTokenResponse;
import com.smartparking.model.response.InfoResponse;
import com.smartparking.security.exception.AuthorizationEx;
import com.smartparking.security.tokens.TokenPair;
import com.smartparking.security.tokens.TokenUtil;
import com.smartparking.security.utils.validation.Validator;
import com.smartparking.service.ClientService;
import com.smartparking.service.SecurityService;
import com.smartparking.service.TemporaryDataConfirmationService;
import com.smartparking.service.email.EmailService;
import com.smartparking.service.impl.ExpirationCheckService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private TemporaryDataConfirmationService temporaryDataConfirmationService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Autowired
    EmailService emailService;

    @Autowired
    private ExpirationCheckService expirationCheckService;

    @Autowired
    ClientService clientService;

    @Value("${cross_origin.client}")
    String hostUrl;

    @PostMapping(value = "/generate-token")
    public ResponseEntity register(@RequestBody LoginRequest loginRequest) throws AuthenticationException {
        final String email;
        final String password;
        final UserDetails user;
        try {
            email = validator.validateEmailOnLogin(loginRequest.getEmail());
            password = validator.validatePassword(loginRequest.getPassword());
        } catch (AuthorizationEx e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InfoResponse(e.getMessage()));
        }
        user = userService.loadUserByUsername(email);
        if (user != null && bcryptEncoder.matches(password, user.getPassword())) {
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final TokenPair tokenPair = tokenUtil.generateTokenPair(user);
            return ResponseEntity.ok(new AuthTokenResponse(tokenPair.getAccessToken(), tokenPair.getRefreshToken()));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InfoResponse("Password is incorrect."));
    }

    @PostMapping("/signup")
    public ResponseEntity saveUser(@RequestBody RegistrationRequest regReq) {
        try {
            securityService.saveClientFromRegistrationRequest(regReq);
        } catch (AuthorizationEx e) {
            LOGGER.warn(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InfoResponse(e.getMessage()));
        }
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        final String confirmUrl = hostUrl + "/activate/" + uuid;
        String email = regReq.getEmail();
        String firstName = regReq.getFirstname();
        temporaryDataConfirmationService.save(
                temporaryDataConfirmationService.makeRegistrationConfirmationEntity(uuid, email));
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        try {
            emailExecutor.execute(() -> {
                emailService.prepareAndSendConfirmRegistrationEmail(email, firstName, confirmUrl);
            });
        } catch (MailException e) {
            LOGGER.error("Could not send email to : {} Error = {}", email, e.getMessage());
        } finally {
            emailExecutor.shutdown();
        }
        return ResponseEntity.status(HttpStatus.OK).body(new InfoResponse("Please check your email, and confirm registration"));
    }

    @PostMapping("/activate")
    public ResponseEntity activateUser(@RequestBody String uuidFromUrl) {
        Optional<TemporaryDataConfirmation> checkedTemporaryDataConfirmation =
                expirationCheckService.getTemporaryDataConfirmationWithExpirationChecking(uuidFromUrl);

        if (checkedTemporaryDataConfirmation.isPresent()) {
            Client client = clientService.findOne(checkedTemporaryDataConfirmation.get().getUserEmail());

            if ((uuidFromUrl.equals(checkedTemporaryDataConfirmation.get().getUuid()))
                    && (checkedTemporaryDataConfirmation.get()
                    .getConfirmationType() == ConfirmationType.REGISTRATION_CONFIRM)) {

                securityService.activateUserByEmail(checkedTemporaryDataConfirmation.get().getUserEmail());
                temporaryDataConfirmationService.delete(checkedTemporaryDataConfirmation.get());

                ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
                try {
                    emailExecutor.execute(() -> {
                        emailService.prepareAndSendWelcomeEmail(client.getEmail(), client.getFirstName());
                    });
                } catch (MailException e) {
                    LOGGER.error("Could not send email to : {} Error = {}", client.getEmail(), e.getMessage());
                } finally {
                    emailExecutor.shutdown();
                }
                return ResponseEntity.status(HttpStatus.OK)
                        .body(new InfoResponse("Your account has been successfully activated"));
            }
        } else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new InfoResponse("Error during account activation"));
        }
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new InfoResponse("Error during account activation"));
    }

    @PostMapping(value = "/social")
    public ResponseEntity socialSignIn(@RequestBody SocialSignInRequest request) {
        UserDetails user = null;
        try {
            user = userService.loadUserByUsername(request.getEmail());
        } catch (Exception e) {
            LOGGER.warn("The user with such an email does not exist.");
            e.getMessage();
        }
        if (user == null) {
            try {
                securityService.saveClientFromSocialSignInRequest(request);
            } catch (AuthorizationEx e) {
                LOGGER.warn(e.getMessage());
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InfoResponse(e.getMessage()));
            }
            user = userService.loadUserByUsername(request.getEmail());
        }
        if (user != null) {
            final TokenPair tokenPair = tokenUtil.generateTokenPair(user);
            return ResponseEntity.ok(new AuthTokenResponse(tokenPair.getAccessToken(), tokenPair.getRefreshToken()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new InfoResponse("Sorry, can`t authorize you now."));
    }
}
