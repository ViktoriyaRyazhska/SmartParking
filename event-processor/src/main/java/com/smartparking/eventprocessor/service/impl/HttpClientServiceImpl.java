package com.smartparking.eventprocessor.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartparking.eventprocessor.config.properties.HttpClientProperties;
import com.smartparking.eventprocessor.controller.exception.FailureException;
import com.smartparking.eventprocessor.model.response.ErrorResponse;
import com.smartparking.eventprocessor.service.HttpClientService;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

@Service
public class HttpClientServiceImpl implements HttpClientService {

    private final HttpClient client = HttpClients.createDefault();
    @Autowired
    private HttpClientProperties httpClientProperties;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public <S> S post(String path, Object request, String token,
                      Class<S> successClass) throws IOException {
        HttpPost httpPost = prepareHttpPost(path, request, token);
        return responseToResult(client.execute(httpPost), successClass);
    }

    @Override
    public <S> List<S> postList(String path, Object request, String token,
                                Class<S> successClass) throws IOException {
        HttpPost httpPost = prepareHttpPost(path, request, token);
        return responseToResultList(client.execute(httpPost), successClass);
    }

    @Override
    public <S> S get(String path, Object request, String token, Class<S> successClass)
            throws IOException {
        HttpGet httpGet = prepareHttpGet(path, token);
        return responseToResult(client.execute(httpGet), successClass);
    }

    @Override
    public <S> List<S> getList(String path, Object request, String token, Class<S> successClass)
            throws IOException {
        HttpGet httpGet = prepareHttpGet(path, token);
        return responseToResultList(client.execute(httpGet), successClass);
    }

    private HttpPost prepareHttpPost(String path, Object request, String token) throws JsonProcessingException {
        HttpPost httpPost = new HttpPost(httpClientProperties.getHost() + path);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        if (token != null) {
            httpPost.setHeader("Authorization", "Bearer " + token);
        }

        StringEntity httpEntity = new StringEntity(objectMapper.writeValueAsString(request), Charset.forName("UTF-8"));
        httpEntity.setContentType("application/json");
        httpPost.setEntity(httpEntity);
        return httpPost;
    }

    private HttpGet prepareHttpGet(String path, String token) {
        HttpGet httpGet = new HttpGet(httpClientProperties.getHost() + path);
        httpGet.setHeader("Accept", "application/json");
        if (token != null) {
            httpGet.setHeader("Authorization", "Bearer " + token);
        }
        return httpGet;
    }

    private <S> S responseToResult(HttpResponse httpResponse,
                                   Class<S> successClass) throws IOException {
        HttpStatus httpStatus = HttpStatus.resolve(httpResponse.getStatusLine().getStatusCode());
        if (httpStatus.is2xxSuccessful()) {
            return objectMapper.readValue(httpResponse.getEntity().getContent(), successClass);
        } else {
            final ErrorResponse content = objectMapper.readValue(
                    httpResponse.getEntity().getContent(), ErrorResponse.class);
            throw new FailureException(httpStatus, content);
        }
    }

    private <S> List<S> responseToResultList(
            HttpResponse httpResponse, Class<S> successClass) throws IOException {
        HttpStatus httpStatus = HttpStatus.resolve(httpResponse.getStatusLine().getStatusCode());
        if (httpStatus.is2xxSuccessful()) {
            return objectMapper.readValue(httpResponse.getEntity().getContent(),
                    objectMapper.getTypeFactory().constructCollectionType(List.class, successClass));
        } else {
            final ErrorResponse content = objectMapper.readValue(
                    httpResponse.getEntity().getContent(), ErrorResponse.class);
            throw new FailureException(httpStatus, content);
        }
    }
}
