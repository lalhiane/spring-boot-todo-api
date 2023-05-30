package com.springtodo.spring_todo_api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.springtodo.spring_todo_api.model.entity.Todo;

public interface TodoRepo extends JpaRepository<Todo, Integer> {

    boolean existsByTitle(String title);

    List<Todo> findByUserId(Integer userId);

    boolean existsByTitleAndUserId(String title, Integer UserId);

}
