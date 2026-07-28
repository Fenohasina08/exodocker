package com.example.demo.endpoint.rest.controller.todo;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.conf.FacadeIT;
import com.example.demo.dto.CreateTodoRequest;
import com.example.demo.dto.TodoResponse;
import com.example.demo.dto.UpdateTodoRequest;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

class TodoControllerIT extends FacadeIT {

  @Autowired private TestRestTemplate restTemplate;

  @Test
  void createTodo_should_create_and_return_201() {

    var request = CreateTodoRequest.builder().title("Ecrire les tests").description("desc").build();

    ResponseEntity<TodoResponse> response =
        restTemplate.postForEntity("/todos", request, TodoResponse.class);

    System.out.println("STATUS = " + response.getStatusCode());
    System.out.println("BODY = " + response.getBody());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    assertThat(response.getBody()).isNotNull();

    assertThat(response.getBody().getId()).isNotBlank();

    assertThat(response.getBody().getTitle()).isEqualTo("Ecrire les tests");

    assertThat(response.getBody().getIsCompleted()).isFalse();
  }

  @Test
  void createTodo_without_title_should_return_400() {

    var request = CreateTodoRequest.builder().title("").description("desc").build();

    ResponseEntity<Object> response = restTemplate.postForEntity("/todos", request, Object.class);

    System.out.println("STATUS = " + response.getStatusCode());
    System.out.println("BODY = " + response.getBody());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
  }

  @Test
  void getTodoById_should_return_the_created_todo() {

    var request = CreateTodoRequest.builder().title("A retrouver").description("desc").build();

    ResponseEntity<TodoResponse> createdResponse =
        restTemplate.postForEntity("/todos", request, TodoResponse.class);

    System.out.println("CREATE STATUS = " + createdResponse.getStatusCode());
    System.out.println("CREATE BODY = " + createdResponse.getBody());

    assertThat(createdResponse.getBody()).isNotNull();

    String id = createdResponse.getBody().getId();

    ResponseEntity<TodoResponse> response =
        restTemplate.getForEntity("/todos/" + id, TodoResponse.class);

    System.out.println("GET STATUS = " + response.getStatusCode());
    System.out.println("GET BODY = " + response.getBody());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThat(response.getBody()).isNotNull();

    assertThat(response.getBody().getId()).isEqualTo(id);

    assertThat(response.getBody().getTitle()).isEqualTo("A retrouver");
  }

  @Test
  void getTodoById_nonExistent_shouldReturn404() {

    ResponseEntity<Object> response =
        restTemplate.getForEntity("/todos/id-inexistant", Object.class);

    System.out.println("STATUS = " + response.getStatusCode());
    System.out.println("BODY = " + response.getBody());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }

  @Test
  void updateTodo_should_modify_the_todo() {

    var createRequest =
        CreateTodoRequest.builder().title("Avant modif").description("desc").build();

    TodoResponse created =
        restTemplate.postForEntity("/todos", createRequest, TodoResponse.class).getBody();

    assertThat(created).isNotNull();

    var updateRequest =
        UpdateTodoRequest.builder()
            .id(created.getId())
            .title("Apres modif")
            .description("nouvelle desc")
            .isCompleted(true)
            .build();

    ResponseEntity<TodoResponse> response =
        restTemplate.exchange(
            RequestEntity.put(java.net.URI.create("/todos")).body(updateRequest),
            TodoResponse.class);

    System.out.println("STATUS = " + response.getStatusCode());
    System.out.println("BODY = " + response.getBody());

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThat(response.getBody()).isNotNull();

    assertThat(response.getBody().getTitle()).isEqualTo("Apres modif");

    assertThat(response.getBody().getIsCompleted()).isTrue();
  }

  @Test
  void getTodos_should_list_todos_and_filter_by_isCompleted() {

    TodoResponse created =
        restTemplate
            .postForEntity(
                "/todos",
                CreateTodoRequest.builder().title("Deja fait").description("desc").build(),
                TodoResponse.class)
            .getBody();

    assertThat(created).isNotNull();

    var updateRequest =
        UpdateTodoRequest.builder()
            .id(created.getId())
            .title(created.getTitle())
            .description(created.getDescription())
            .isCompleted(true)
            .build();

    restTemplate.exchange(
        RequestEntity.put(java.net.URI.create("/todos")).body(updateRequest), TodoResponse.class);

    ResponseEntity<List<TodoResponse>> allTodos =
        restTemplate.exchange(
            "/todos",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<TodoResponse>>() {});

    ResponseEntity<List<TodoResponse>> completedTodos =
        restTemplate.exchange(
            "/todos?onlyCompleted=true",
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<TodoResponse>>() {});

    System.out.println("ALL STATUS = " + allTodos.getStatusCode());
    System.out.println("ALL BODY = " + allTodos.getBody());

    System.out.println("COMPLETED STATUS = " + completedTodos.getStatusCode());
    System.out.println("COMPLETED BODY = " + completedTodos.getBody());

    assertThat(allTodos.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThat(allTodos.getBody()).isNotNull();

    assertThat(allTodos.getBody()).isNotEmpty();

    assertThat(completedTodos.getStatusCode()).isEqualTo(HttpStatus.OK);

    assertThat(completedTodos.getBody()).isNotNull();

    assertThat(completedTodos.getBody()).allMatch(TodoResponse::getIsCompleted);
  }
}
