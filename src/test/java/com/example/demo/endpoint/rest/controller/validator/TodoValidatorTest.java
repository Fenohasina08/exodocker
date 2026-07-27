package com.example.demo.endpoint.rest.controller.validator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.example.demo.dto.CreateTodoRequest;
import com.example.demo.dto.UpdateTodoRequest;
import com.example.demo.validator.TodoValidator;
import org.junit.jupiter.api.Test;

class TodoValidatorTest {

  private final TodoValidator validator = new TodoValidator();

  @Test
  void validateCreate_with_valid_title_should_not_throw_anything() {
    var request = CreateTodoRequest.builder().title("Titre valide").description("desc").build();

    assertThatCode(() -> validator.validateCreate(request)).doesNotThrowAnyException();
  }

  @Test
  void validateCreate_untitled_should_throw_an_exception() {
    var request = CreateTodoRequest.builder().title(null).description("desc").build();

    assertThatThrownBy(() -> validator.validateCreate(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Title cannot be empty");
  }

  @Test
  void validateCreate_with_empty_title_should_throw_an_exception() {
    var request = CreateTodoRequest.builder().title("   ").description("desc").build();

    assertThatThrownBy(() -> validator.validateCreate(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Title cannot be empty");
  }

  @Test
  void validateUpdate_with_valid_id_and_title_should_not_throw_anything() {
    var request =
        UpdateTodoRequest.builder().id("some-id").title("Titre valide").description("desc").build();

    assertThatCode(() -> validator.validateUpdate(request)).doesNotThrowAnyException();
  }

  @Test
  void validateUpdate_without_id_should_throw_an_exception() {
    var request = UpdateTodoRequest.builder().id(null).title("Titre valide").build();

    assertThatThrownBy(() -> validator.validateUpdate(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Id cannot be empty");
  }

  @Test
  void validateUpdate_untitled_should_throw_an_exception() {
    var request = UpdateTodoRequest.builder().id("some-id").title(" ").build();

    assertThatThrownBy(() -> validator.validateUpdate(request))
        .isInstanceOf(IllegalArgumentException.class)
        .hasMessage("Title cannot be empty");
  }
}
