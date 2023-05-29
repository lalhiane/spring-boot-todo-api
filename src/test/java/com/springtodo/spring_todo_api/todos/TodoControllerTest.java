package com.springtodo.spring_todo_api.todos;

import java.util.Arrays;

import java.util.List;

import org.junit.Test;

import org.mockito.Mockito;

import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.http.MediaType;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.springtodo.spring_todo_api.AbstractTodoAppTest;

import com.springtodo.spring_todo_api.model.entity.Todo;

import com.springtodo.spring_todo_api.services.TodoService;

import static org.mockito.BDDMockito.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.*;

public class TodoControllerTest extends AbstractTodoAppTest {

    @MockBean
    private TodoService todoService;

    @Test
    public void whenGetAllTodos_thenReturnJsonArray() throws Exception {

        // mockMvc.perform(get("/api/v1/todos")).andExpect(status().isOk());

        Todo todo1 = new Todo(1, "Todo 1", "This Is Todo 1", 1);

        Todo todo2 = new Todo(2, "Todo 2", "This Is Todo 2", 1);

        List<Todo> target_data = Arrays.asList(todo1, todo2);

        given(todoService.findByUserId(1)).willReturn(target_data);

        mockMvc.perform(doGetRequest("/api/v1/todos").contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$", hasSize(2)))

                .andExpect(jsonPath("$[0].title", equalTo(todo1.getTitle())));

    }

    @Test
    public void whenIPostNewTodo_thenCreateNewTodo() throws Exception {

        Todo todo1 = new Todo();

        todo1.setTitle("Hadi Raha Todo");

        todo1.setDescription("This Is Todo...");

        todo1.setUserId(1);

        given(todoService.addNew(Mockito.any(Todo.class))).willReturn(todo1);

        ObjectMapper mapper = new ObjectMapper();

        mockMvc.perform(

                doPostRequest("/api/v1/todos/")

                        .content(mapper.writeValueAsString(todo1))

        )

                .andExpect(status().isCreated());

    }

}
