package com.springtodo.spring_todo_api.services;

import java.util.List;

import java.util.NoSuchElementException;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.springtodo.spring_todo_api.Errors.ConflictException;

import com.springtodo.spring_todo_api.Errors.NotFoundException;

import com.springtodo.spring_todo_api.model.dto.TodoDto;

import com.springtodo.spring_todo_api.model.entity.Todo;

import com.springtodo.spring_todo_api.repositories.TodoRepo;

@Service
public class TodoService {

    @Autowired
    private TodoRepo todoRepo;

    // public List<Todo> findAll() {

    // return todoRepo.findAll();

    // }

    public List<Todo> findByUserId(Integer userId) {

        return todoRepo.findByUserId(userId);

    }

    public TodoDto findById(Integer id, Integer userId) {

        try {

            Optional<Todo> todo = todoRepo.findById(id);

            TodoDto todoDto = TodoDto.fromEntityToDto(todo.get());

            if (todoDto.getUserId() == userId) {

                return todoDto;

            } else {

                throw new NotFoundException("No Record With The Id " + id + " Was Found!");

            }

        } catch (NoSuchElementException ex) {

            throw new NotFoundException("No Record With The Id " + id + " Was Found!");

        }

    }

    public Todo addNew(Todo todo) {

        // if (!todoRepo.existsByTitle(todo.getTitle())) {

        // return todoRepo.save(todo);

        // } else {

        // throw new ConflictException(String.format("Todo With Title '%s' Is Already
        // Exists!", todo.getTitle()));

        // }

        boolean flag = false;

        for (Todo t : findByUserId(todo.getUserId())) {

            if (t.getTitle().equals(todo.getTitle())) {

                flag = true;

                break;

            }

        }

        if (!flag) {

            return todoRepo.save(todo);

        } else {

            throw new ConflictException(String.format("Todo With Title '%s' Is Already Exists!", todo.getTitle()));

        }

    }

    public void deleteById(Integer id, Integer userId) {

        if (findById(id, userId) != null) {

            todoRepo.deleteById(id);

        }

    }

}
