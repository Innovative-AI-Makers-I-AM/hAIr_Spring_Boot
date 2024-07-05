package com.example.demo.tempcontroller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @PostMapping("/endpoint")
    public ResponseEntity<String> receiveData(@RequestBody Map<String, String> payload) {
        String data = payload.get("data");

        return ResponseEntity.ok("Data received : " + data);
    }
}
