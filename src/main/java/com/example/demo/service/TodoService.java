package com.example.demo.service;

import com.example.demo.dto.CreateTodoRequest;
import com.example.demo.dto.UpdateTodoRequest;
import com.example.demo.entity.JTodo;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.TodoMapper;
import com.example.demo.model.Todo;
import com.example.demo.repository.JTodoRepository;
import com.example.demo.validator.TodoValidator;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TodoService {

  private final JTodoRepository todoRepository;

  private final TodoMapper todoMapper;

  private final TodoValidator todoValidator;

  public List<Todo> getAllTodos() {
    return todoRepository.findAll().stream().map(todoMapper::toModel).collect(Collectors.toList());
  }

  public List<Todo> getTodosByCompleted(Boolean completed) {
    return todoRepository.findByIsCompleted(completed).stream()
        .map(todoMapper::toModel)
        .collect(Collectors.toList());
  }

  public Todo getTodoById(String id) {
    JTodo todo =
        todoRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Todo not found with id : " + id));

    return todoMapper.toModel(todo);
  }

  @Transactional
  public Todo createTodo(CreateTodoRequest request) {

    todoValidator.validateCreate(request);

    JTodo todo = todoMapper.createRequestToEntity(request);

    todo.setId(UUID.randomUUID().toString());
    todo.setCreatedAt(Instant.now());
    todo.setUpdatedAt(Instant.now());

    JTodo savedTodo = todoRepository.save(todo);

    return todoMapper.toModel(savedTodo);
  }

  @Transactional
  public Todo updateTodo(UpdateTodoRequest request) {

    todoValidator.validateUpdate(request);

    JTodo todo =
        todoRepository
            .findById(request.getId())
            .orElseThrow(
                () -> new NotFoundException("Todo not found with id : " + request.getId()));

    todoMapper.updateEntity(request, todo);

    todo.setUpdatedAt(Instant.now());

    JTodo updatedTodo = todoRepository.save(todo);

    return todoMapper.toModel(updatedTodo);
  }
}
