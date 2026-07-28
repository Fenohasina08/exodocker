package com.example.demo.exception;

import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> handleNotFound(NotFoundException exception) {

    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(Map.of("message", exception.getMessage(), "status", 404, "timestamp", Instant.now()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidation(MethodArgumentNotValidException exception) {

    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of("message", "Validation error", "status", 400, "timestamp", Instant.now()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException exception) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Map.of("message", exception.getMessage(), "status", 400, "timestamp", Instant.now()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleGeneric(Exception exception) {

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
        .body(Map.of("message", exception.getMessage(), "status", 500, "timestamp", Instant.now()));
  }
}
