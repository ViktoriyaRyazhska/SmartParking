package com.smartparking.eventprocessor.service;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.http.HttpStatus;

import java.io.IOException;

public interface HttpClientService {

    <S> S postAndReceiveBody(String path, Object request, String token, TypeReference<S> successTypeReference) throws IOException;

    HttpStatus postAndReceiveStatus(String path, Object request, String token) throws IOException;

    <S> S getAndReceiveBody(String path, Object request, String token, TypeReference<S> successTypeReference) throws IOException;
}
