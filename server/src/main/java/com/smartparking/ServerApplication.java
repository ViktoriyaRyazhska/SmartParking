package com.smartparking;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;

@SpringBootApplication
@EnableWebMvc
@EnableConfigurationProperties
public class ServerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ServerApplication.class, args);
        System.out.println("EXIT AND CLOSE fsdgahsjdgasjhdgf");
    }
}
