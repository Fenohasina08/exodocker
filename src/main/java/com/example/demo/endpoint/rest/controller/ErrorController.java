package com.example.demo.endpoint.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ErrorController {

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
