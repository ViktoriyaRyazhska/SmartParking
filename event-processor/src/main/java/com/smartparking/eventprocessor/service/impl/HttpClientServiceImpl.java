package com.smartparking.eventprocessor.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.smartparking.eventprocessor.config.properties.HttpClientProperties;
import com.smartparking.eventprocessor.model.response.ErrorResponse;
import com.smartparking.eventprocessor.service.HttpClientService;
import com.smartparking.eventprocessor.service.impl.exception.HttpStatusException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

@Service
public class HttpClientServiceImpl implements HttpClientService {
    private SSLContext sslContext;
    private HttpClient client;

    public HttpClientServiceImpl() {
        initHttpClient();
    }

    @Autowired
    private HttpClientProperties httpClientProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public <S> S postAndReceiveBody(String path, Object request, String token, TypeReference<S> typeReference) throws IOException {
        HttpPost httpPost = prepareHttpPost(path, request, token);
        HttpResponse response = client.execute(httpPost);
        return responseToBody(response, typeReference);
    }

    @Override
    public HttpStatus postAndReceiveStatus(String path, Object request, String token) throws IOException {
        HttpPost httpPost = prepareHttpPost(path, request, token);
        HttpResponse response = client.execute(httpPost);
        return responseToStatus(response);
    }

    @Override
    public <S> S getAndReceiveBody(String path, Object request, String token, TypeReference<S> typeReference)
            throws IOException {
        HttpGet httpGet = prepareHttpGet(path, token);
        HttpResponse response = client.execute(httpGet);
        return responseToBody(response, typeReference);
    }

    private HttpPost prepareHttpPost(String path, Object request, String token) throws JsonProcessingException {
        HttpPost httpPost = new HttpPost(httpClientProperties.getServerUrl() + path);
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
        HttpGet httpGet = new HttpGet(httpClientProperties.getServerUrl() + path);
        httpGet.setHeader("Accept", "application/json");
        if (token != null) {
            httpGet.setHeader("Authorization", "Bearer " + token);
        }
        return httpGet;
    }

    private <S> S responseToBody(HttpResponse httpResponse, TypeReference<S> typeReference) throws IOException {
        HttpStatus httpStatus = HttpStatus.resolve(httpResponse.getStatusLine().getStatusCode());
        if (httpStatus.is2xxSuccessful()) {
            return objectMapper.readValue(httpResponse.getEntity().getContent(), typeReference);
        } else {
            final ErrorResponse content = objectMapper.readValue(
                    httpResponse.getEntity().getContent(), ErrorResponse.class);
            throw new HttpStatusException(httpStatus, content);
        }
    }

    private HttpStatus responseToStatus(HttpResponse httpResponse) throws IOException {
        HttpStatus httpStatus = HttpStatus.resolve(httpResponse.getStatusLine().getStatusCode());
        if (httpStatus.is2xxSuccessful()) {
            return httpStatus;
        } else {
            final ErrorResponse content = objectMapper.readValue(
                    httpResponse.getEntity().getContent(), ErrorResponse.class);
            throw new HttpStatusException(httpStatus, content);
        }
    }

    private void initHttpClient() {
        try {
            sslContext = new SSLContextBuilder().loadTrustMaterial(null, (certificate, authType) -> true).build();
            client = HttpClients.custom()
                    .setSSLContext(sslContext)
                    .setSSLHostnameVerifier(new NoopHostnameVerifier())
                    .build();
//            client = HttpClients.createDefault();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        }
    }
}
