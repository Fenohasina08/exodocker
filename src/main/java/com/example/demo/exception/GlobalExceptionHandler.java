package com.example.demo.exception;

import io.sentry.Sentry;
import jakarta.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<?> handleNotFound(NotFoundException exception, HttpServletRequest request) {
    // 404 = comportement attendu, pas une anomalie -> pas de Sentry, juste un log léger
    log.info("Resource not found: {} ({})", exception.getMessage(), request.getRequestURI());
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(baseBody(exception.getMessage(), 404));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<?> handleValidation(
      MethodArgumentNotValidException exception, HttpServletRequest request) {
    Map<String, String> fieldErrors = new LinkedHashMap<>();
    for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
      fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
    }
    log.info("Validation error on {}: {}", request.getRequestURI(), fieldErrors);

    Map<String, Object> body = baseBody("Validation error", 400);
    body.put("errors", fieldErrors);
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<?> handleIllegalArgument(
      IllegalArgumentException exception, HttpServletRequest request) {
    log.info("Bad request on {}: {}", request.getRequestURI(), exception.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(baseBody(exception.getMessage(), 400));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<?> handleGeneric(Exception exception, HttpServletRequest request) {
    // Ici seulement : vraie anomalie -> Sentry + log ERROR + id de suivi
    String errorId = UUID.randomUUID().toString();
    Sentry.configureScope(
        scope -> {
          scope.setTag("errorId", errorId);
          scope.setTag("path", request.getRequestURI());
        });
    Sentry.captureException(exception);

    log.error("Unhandled exception [{}] on {}", errorId, request.getRequestURI(), exception);

    Map<String, Object> body = baseBody("Internal server error", 500);
    body.put("errorId", errorId);
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
  }

  private Map<String, Object> baseBody(String message, int status) {
    Map<String, Object> body = new LinkedHashMap<>();
    body.put("message", message);
    body.put("status", status);
    body.put("timestamp", Instant.now());
    return body;
  }
}
