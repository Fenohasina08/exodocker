package com.example.demo.repository;

import com.example.demo.entity.JTodo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JTodoRepository extends JpaRepository<JTodo, String> {

  List<JTodo> findByIsCompleted(Boolean isCompleted);
}
