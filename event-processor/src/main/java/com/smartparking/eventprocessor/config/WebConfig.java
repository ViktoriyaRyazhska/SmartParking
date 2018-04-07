package com.smartparking.eventprocessor.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
@ComponentScan({"com.smartparking.eventprocessor.controller", "com.smartparking.eventprocessor.service"})
public class WebConfig implements WebMvcConfigurer {
}
