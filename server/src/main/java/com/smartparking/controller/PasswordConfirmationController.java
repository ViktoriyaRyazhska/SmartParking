package com.smartparking.controller;

import com.smartparking.entity.PasswordConfirmation;
import com.smartparking.model.request.PasswordRequest;
import com.smartparking.model.response.InfoResponse;
import com.smartparking.service.ClientService;
import com.smartparking.service.PasswordConfirmationService;
import com.smartparking.service.email.EmailService;
import com.smartparking.service.impl.ExpirationCheckService;
import com.smartparking.service.impl.SecurityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class PasswordConfirmationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordConfirmationController.class);

    @Autowired
    private PasswordConfirmationService passwordConfirmationService;

    @Autowired
    private ExpirationCheckService expirationCheckService;

    @Autowired
    SecurityServiceImpl securityServiceImpl;

    @Autowired
    EmailService emailService;

    @Autowired
    ClientService clientService;

    @Value("${cross_origin.client}")
    String hostUrl;

    @PostMapping(value = "/update/password")
    public ResponseEntity saveUser(@RequestBody String uuidFromUrl) {
        PasswordConfirmation checkedPasswordConfirmation =
                expirationCheckService.getPasswordConfirmationWithExpirationChecking(uuidFromUrl);

        if (uuidFromUrl.equals(checkedPasswordConfirmation.getUuid())) {
            securityServiceImpl.updateClientEncodedPassword(checkedPasswordConfirmation.getNewPassword());
            passwordConfirmationService.delete(checkedPasswordConfirmation);
            return ResponseEntity.status(HttpStatus.OK).body(new InfoResponse("You are successfully updated password"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new InfoResponse("Error during password changing"));
        }
    }

    @PostMapping("/update/password/confirm")
    public ResponseEntity sendConfirmation(@RequestBody PasswordRequest passwordRequest) {
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        final String confirmUrl = hostUrl + "/update/password/" + uuid;
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String firstName = clientService.findOne(email).getFirstName();
        passwordConfirmationService.save(
                passwordConfirmationService.makePasswordConfirmationEntity(uuid, passwordRequest.getPassword()));
        try {
            emailService.prepareAndSendConfirmPassEmail(email, firstName, confirmUrl);
        } catch (MailException e) {
            LOGGER.error("Could not send email to : {} Error = {}", email, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email sending Error!");
        }
        return ResponseEntity.status(HttpStatus.OK).body(new InfoResponse("Data saved successfully"));
    }
}
