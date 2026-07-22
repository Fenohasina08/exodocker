package com.example.demo.dto;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TodoResponse {

  private String id;

  private String title;

  private String description;

  private Boolean isCompleted;

  private Instant createdAt;

  private Instant updatedAt;
}
