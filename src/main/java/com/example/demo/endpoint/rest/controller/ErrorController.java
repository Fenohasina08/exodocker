package com.example.demo.endpoint.rest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

    @GetMapping("/exception")
    public void getError() {
        throw new RuntimeException("This is an error");
    }
}