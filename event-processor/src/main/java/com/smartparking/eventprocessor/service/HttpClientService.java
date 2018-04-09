package com.smartparking.eventprocessor.service;

import java.io.IOException;
import java.util.List;

public interface HttpClientService {

    <S> S post(String path, Object request, String token, Class<S> successClass) throws IOException;

    <S> S get(String path, Object request, String token, Class<S> successClass) throws IOException;

    <S> List<S> postList(String path, Object request, String token, Class<S> successClass) throws IOException;

    <S> List<S> getList(String path, Object request, String token, Class<S> successClass) throws IOException;
}
