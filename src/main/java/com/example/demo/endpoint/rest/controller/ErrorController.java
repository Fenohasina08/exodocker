package com.example.demo.endpoint.rest.controller;

import io.sentry.Sentry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController {

  @GetMapping("/log-error")
  public ResponseEntity<String> logError() {

    try {
      throw new Exception("This is a test.");
    } catch (Exception e) {
      Sentry.captureException(e);
    }

    return ResponseEntity.ok("Error has been sent to Sentry");
  }
}
