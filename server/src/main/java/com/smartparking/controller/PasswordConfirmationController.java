package com.smartparking.controller;

import com.smartparking.entity.ConfirmationType;
import com.smartparking.entity.TemporaryDataConfirmation;
import com.smartparking.model.request.PasswordRequest;
import com.smartparking.model.response.InfoResponse;
import com.smartparking.service.ClientService;
import com.smartparking.service.TemporaryDataConfirmationService;
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

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RestController
public class PasswordConfirmationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordConfirmationController.class);

    @Autowired
    private TemporaryDataConfirmationService temporaryDataConfirmationService;

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

    @PostMapping(value = "/profile/update/password")
    public ResponseEntity saveUserPassword(@RequestBody String uuidFromUrl) {
        Optional<TemporaryDataConfirmation> checkedTemporaryDataConfirmation =
                expirationCheckService.getTemporaryDataConfirmationWithExpirationChecking(uuidFromUrl);
        if (checkedTemporaryDataConfirmation.isPresent()) {
            if ((uuidFromUrl.equals(checkedTemporaryDataConfirmation.get().getUuid()))
                    && (checkedTemporaryDataConfirmation.get()
                    .getConfirmationType() == ConfirmationType.PASSWORD_CONFIRM)) {
                String newPassword = checkedTemporaryDataConfirmation.get().getNewPassword();
                String userEmail = checkedTemporaryDataConfirmation.get().getUserEmail();

                securityServiceImpl.updateClientEncodedPassword(newPassword, userEmail);
                temporaryDataConfirmationService.delete(checkedTemporaryDataConfirmation.get());

                return ResponseEntity.status(HttpStatus.OK)
                        .body(new InfoResponse("You are successfully updated password"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new InfoResponse("Error during password changing"));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new InfoResponse("Error during password changing"));
    }

    @PostMapping("/profile/update/password/confirm")
    public ResponseEntity sendConfirmation(@RequestBody PasswordRequest passwordRequest) {
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        final String confirmUrl = hostUrl + "/update/password/" + uuid;
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        String firstName = clientService.findOne(email).getFirstName();
        temporaryDataConfirmationService.save(
                temporaryDataConfirmationService
                        .makePasswordConfirmationEntity(uuid, passwordRequest.getPassword(), email));
        sendPasswordChangeConfirmationEmail(email, firstName, confirmUrl);
        return ResponseEntity.status(HttpStatus.OK).body(new InfoResponse("Data saved successfully"));
    }

    @PostMapping("/profile/forget/password/confirm")
    public ResponseEntity forgetPasswordSendConfirmation(@RequestBody PasswordRequest passwordRequest) {
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        final String confirmUrl = hostUrl + "/update/password/" + uuid;
        String userEmail = passwordRequest.getEmail();
        String firstName = clientService.findOne(userEmail).getFirstName();
        temporaryDataConfirmationService.save(
                temporaryDataConfirmationService
                        .makePasswordConfirmationEntity(uuid, passwordRequest.getPassword(), userEmail));
        sendPasswordChangeConfirmationEmail(userEmail, firstName, confirmUrl);
        return ResponseEntity.status(HttpStatus.OK).body(new InfoResponse("Data saved successfully"));
    }

    private void sendPasswordChangeConfirmationEmail(String userEmail, String firstName, String confirmUrl) {
        ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
        try {
            emailExecutor.execute(() -> {
                emailService.prepareAndSendConfirmPassEmail(userEmail, firstName, confirmUrl);
            });
        }catch (MailException e) {
            LOGGER.error("Could not send email to : {} Error = {}", userEmail, e.getMessage());
        }finally {
            emailExecutor.shutdown();
        }
    }
}
