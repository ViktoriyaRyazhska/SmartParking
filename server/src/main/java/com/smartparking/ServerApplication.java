package com.smartparking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;

@SpringBootApplication
@EnableConfigurationProperties
@EnableScheduling
public class ServerApplication {

    public static void main(String[] args) throws IOException {
        SpringApplication.run(ServerApplication.class, args);
        System.out.println("EXIT AND CLOSE fsdgahsjdgasjhdgf");
    }
}
