package com.example.demo.endpoint.rest.controller.todo;

import com.example.demo.dto.CreateTodoRequest;
import com.example.demo.dto.TodoResponse;
import com.example.demo.dto.UpdateTodoRequest;
import com.example.demo.mapper.TodoMapper;
import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/todos")
@RequiredArgsConstructor
public class TodoController {

  private final TodoService todoService;

  private final TodoMapper todoMapper;

  @GetMapping
  public ResponseEntity<List<TodoResponse>> getTodos(
      @RequestParam(name = "onlyCompleted", required = false) Boolean onlyCompleted) {

    List<Todo> todos;

    if (onlyCompleted != null) {

      todos = todoService.getTodosByCompleted(onlyCompleted);

    } else {

      todos = todoService.getAllTodos();
    }

    List<TodoResponse> responses =
        todos.stream().map(todoMapper::toResponse).collect(Collectors.toList());

    try {
      throw new Exception("This is a test.");
    } catch (Exception e) {
      Sentry.captureException(e);
    }
    return ResponseEntity.ok(responses);
  }

  @GetMapping("/{id}")
  public ResponseEntity<TodoResponse> getTodoById(@PathVariable String id) {

    Todo todo = todoService.getTodoById(id);

    return ResponseEntity.ok(todoMapper.toResponse(todo));
  }

  @PutMapping
  public ResponseEntity<TodoResponse> updateTodo(@Valid @RequestBody UpdateTodoRequest request) {

    Todo todo = todoService.updateTodo(request);

    return ResponseEntity.ok(todoMapper.toResponse(todo));
  }

  @PostMapping
  public ResponseEntity<TodoResponse> createTodo(@Valid @RequestBody CreateTodoRequest request) {

    Todo todo = todoService.createTodo(request);

    return ResponseEntity.status(201).body(todoMapper.toResponse(todo));
  }
}
