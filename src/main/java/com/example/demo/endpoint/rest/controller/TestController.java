package com.example.demo.endpoint.rest.controller;

import static org.reflections.Reflections.log;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

  @GetMapping("/exception")
  public void getError() {
    throw new RuntimeException("This is an error");
  }

  @GetMapping("/log-error")
  public String logError() {
    log.error("This is an error");
    return "OK";
  }
}
