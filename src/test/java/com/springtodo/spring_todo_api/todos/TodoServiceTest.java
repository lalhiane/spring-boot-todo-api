package com.springtodo.spring_todo_api.todos;

import java.util.Arrays;

import java.util.List;

import org.junit.Test;

import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.*;

import static org.mockito.BDDMockito.*;

import org.mockito.Mock;

import org.springframework.boot.test.context.TestConfiguration;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.context.annotation.Bean;

import org.springframework.test.context.junit4.SpringRunner;

import com.springtodo.spring_todo_api.Errors.NotFoundException;

import com.springtodo.spring_todo_api.model.dto.TodoDto;

import com.springtodo.spring_todo_api.model.entity.Todo;

import com.springtodo.spring_todo_api.repositories.TodoRepo;

import com.springtodo.spring_todo_api.services.TodoService;

@RunWith(SpringRunner.class) // run with junit (springrunner => alias junit)
public class TodoServiceTest {

    @MockBean
    private TodoRepo todoRepo;

    @Mock
    private TodoService todoService;

    @TestConfiguration
    static class TodoServiceContextConfiguration {

        @Bean
        public TodoService todoService() {

            return new TodoService();

        }

    }

    @Test
    public void whenICalledFindAll_returnAllTodos() {

        Todo todo1 = new Todo(1, "Todo 1", "This Is Todo 1", 1);

        Todo todo2 = new Todo(2, "Todo 2", "This Is Todo 2", 1);

        List<Todo> target_data = Arrays.asList(todo1, todo2);

        given(todoRepo.findByUserId(1)).willReturn(target_data);

        assertThat(todoRepo.findByUserId(1))
                .hasSize(2)
                .contains(todo1, todo2);

    }

    @Test
    public void whenGetById_todoShouldBeFound() {

        TodoDto todo1 = new TodoDto(1, "Todo 1", "This Is Todo 1", 1);

        given(todoService.findById(1, 1)).willReturn(todo1);

        assertThat(todoService.findById(1, 1).getTitle()).containsIgnoringCase("Todo 1");

    }

    @Test(expected = NotFoundException.class)
    public void whenGetByInvalidId_todoShouldNotBeFound() {

        given(todoRepo.findById(5)).willThrow(NotFoundException.class);

        todoRepo.findById(5);

    }

}
