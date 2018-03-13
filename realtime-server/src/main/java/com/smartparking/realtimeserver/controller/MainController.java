package com.smartparking.realtimeserver.controller;

import com.smartparking.realtimeserver.model.response.TestResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @RequestMapping("test")
    public ResponseEntity<TestResponse> test() {
        return ResponseEntity.ok(new TestResponse(42, "REST works!"));
    }
}