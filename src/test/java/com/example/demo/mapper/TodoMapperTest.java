package com.example.demo.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.demo.dto.CreateTodoRequest;
import com.example.demo.dto.UpdateTodoRequest;
import com.example.demo.entity.JTodo;
import com.example.demo.model.Todo;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class TodoMapperTest {

  private final TodoMapper mapper = new TodoMapper();

  @Test
  void toModel_should_copy_all_fields() {
    Instant now = Instant.now();
    JTodo entity =
        JTodo.builder()
            .id("id-1")
            .title("Titre")
            .description("Description")
            .isCompleted(true)
            .createdAt(now)
            .updatedAt(now)
            .build();

    Todo model = mapper.toModel(entity);

    assertThat(model.getId()).isEqualTo("id-1");
    assertThat(model.getTitle()).isEqualTo("Titre");
    assertThat(model.getDescription()).isEqualTo("Description");
    assertThat(model.getIsCompleted()).isTrue();
    assertThat(model.getCreatedAt()).isEqualTo(now);
    assertThat(model.getUpdatedAt()).isEqualTo(now);
  }

  @Test
  void createRequestToEntity_should_initialize_isCompleted_to_false() {
    var request = CreateTodoRequest.builder().title("Nouveau").description("desc").build();

    JTodo entity = mapper.createRequestToEntity(request);

    assertThat(entity.getTitle()).isEqualTo("Nouveau");
    assertThat(entity.getDescription()).isEqualTo("desc");
    assertThat(entity.getIsCompleted()).isFalse();
    assertThat(entity.getCreatedAt()).isNotNull();
    assertThat(entity.getUpdatedAt()).isNotNull();
  }

  @Test
  void updateEntity_should_overwrite_mutable_fields() {
    JTodo entity =
        JTodo.builder()
            .id("id-1")
            .title("Ancien titre")
            .description("Ancienne desc")
            .isCompleted(false)
            .createdAt(Instant.now())
            .build();

    var request =
        UpdateTodoRequest.builder()
            .id("id-1")
            .title("Nouveau titre")
            .description("Nouvelle desc")
            .isCompleted(true)
            .build();

    mapper.updateEntity(request, entity);

    assertThat(entity.getTitle()).isEqualTo("Nouveau titre");
    assertThat(entity.getDescription()).isEqualTo("Nouvelle desc");
    assertThat(entity.getIsCompleted()).isTrue();
    assertThat(entity.getUpdatedAt()).isNotNull();
  }

  @Test
  void toResponse_should_copy_all_fields() {
    Instant now = Instant.now();
    Todo model =
        Todo.builder()
            .id("id-1")
            .title("Titre")
            .description("Description")
            .isCompleted(true)
            .createdAt(now)
            .updatedAt(now)
            .build();

    var response = mapper.toResponse(model);

    assertThat(response.getId()).isEqualTo("id-1");
    assertThat(response.getTitle()).isEqualTo("Titre");
    assertThat(response.getDescription()).isEqualTo("Description");
    assertThat(response.getIsCompleted()).isTrue();
    assertThat(response.getCreatedAt()).isEqualTo(now);
    assertThat(response.getUpdatedAt()).isEqualTo(now);
  }
}
