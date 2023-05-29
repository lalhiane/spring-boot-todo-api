package com.springtodo.spring_todo_api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;

import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.springtodo.spring_todo_api.model.dto.TodoDto;

import com.springtodo.spring_todo_api.model.entity.Todo;

import com.springtodo.spring_todo_api.services.TodoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController extends BaseController {

    @Autowired
    private TodoService todoService;

    @GetMapping(value = { "", "/" })
    public ResponseEntity<List<Todo>> getAllTodos() {

        return new ResponseEntity<List<Todo>>(todoService.findByUserId(getCurrentUser().getId()), HttpStatus.OK);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TodoDto> getTodoById(@PathVariable Integer id) {

        return new ResponseEntity<TodoDto>(todoService.findById(id, getCurrentUser().getId()), HttpStatus.OK);

    }

    @PostMapping({ "/", "" })
    public ResponseEntity<Todo> addNewTodo(@RequestBody @Valid Todo todo) {

        todo.setUserId(getCurrentUser().getId());

        return new ResponseEntity<Todo>(todoService.addNew(todo), HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodoById(@PathVariable Integer id) {

        todoService.deleteById(id, getCurrentUser().getId());

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);

    }

}
