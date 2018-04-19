package com.smartparking.service;

import com.smartparking.model.request.PasswordRequest;
import com.smartparking.model.request.RegistrationRequest;
import com.smartparking.model.request.SocialSignInRequest;
import com.smartparking.security.exception.*;

public interface SecurityService {
    void saveClientFromRegistrationRequest(RegistrationRequest registrationRequest) throws EmailValidationEx, NonMatchingPasswordsEx, PasswordValidationEx, FirstnameValidationEx, LastnameValidationEx, DuplicateEmailEx;
    void saveClientFromSocialSignInRequest(SocialSignInRequest socialSignInRequest) throws EmailValidationEx, DuplicateEmailEx, FirstnameValidationEx, LastnameValidationEx;
    void updateClientPassword(PasswordRequest passwordRequest) throws NonMatchingPasswordsEx, PasswordValidationEx;
    void activateUserByEmail (String email);
}
