package com.example.demo.mapper;

import com.example.demo.dto.CreateTodoRequest;
import com.example.demo.dto.TodoResponse;
import com.example.demo.dto.UpdateTodoRequest;
import com.example.demo.entity.JTodo;
import com.example.demo.model.Todo;
import java.time.Instant;
import org.springframework.stereotype.Component;

@Component
public class TodoMapper {

  public Todo toModel(JTodo entity) {

    return Todo.builder()
        .id(entity.getId())
        .title(entity.getTitle())
        .description(entity.getDescription())
        .isCompleted(entity.getIsCompleted())
        .createdAt(entity.getCreatedAt())
        .updatedAt(entity.getUpdatedAt())
        .build();
  }

  public JTodo toEntity(Todo model) {

    return JTodo.builder()
        .id(model.getId())
        .title(model.getTitle())
        .description(model.getDescription())
        .isCompleted(model.getIsCompleted())
        .createdAt(model.getCreatedAt())
        .updatedAt(model.getUpdatedAt())
        .build();
  }

  public JTodo createRequestToEntity(CreateTodoRequest request) {

    return JTodo.builder()
        .title(request.getTitle())
        .description(request.getDescription())
        .isCompleted(false)
        .createdAt(Instant.now())
        .updatedAt(Instant.now())
        .build();
  }

  public void updateEntity(UpdateTodoRequest request, JTodo entity) {

    entity.setTitle(request.getTitle());

    entity.setDescription(request.getDescription());

    entity.setIsCompleted(request.getIsCompleted());

    entity.setUpdatedAt(Instant.now());
  }

  public TodoResponse toResponse(Todo model) {

    return TodoResponse.builder()
        .id(model.getId())
        .title(model.getTitle())
        .description(model.getDescription())
        .isCompleted(model.getIsCompleted())
        .createdAt(model.getCreatedAt())
        .updatedAt(model.getUpdatedAt())
        .build();
  }
}
