package com.example.demo.validator;

import com.example.demo.dto.CreateTodoRequest;
import com.example.demo.dto.UpdateTodoRequest;
import org.springframework.stereotype.Component;

@Component
public class TodoValidator {

  public void validateCreate(CreateTodoRequest request) {

    if (request.getTitle() == null || request.getTitle().isBlank()) {

      throw new IllegalArgumentException("Title cannot be empty");
    }
  }

  public void validateUpdate(UpdateTodoRequest request) {

    if (request.getId() == null || request.getId().isBlank()) {

      throw new IllegalArgumentException("Id cannot be empty");
    }

    if (request.getTitle() == null || request.getTitle().isBlank()) {

      throw new IllegalArgumentException("Title cannot be empty");
    }
  }
}
