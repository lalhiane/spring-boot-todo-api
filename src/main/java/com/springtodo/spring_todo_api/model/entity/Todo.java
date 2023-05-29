package com.springtodo.spring_todo_api.model.entity;

import com.springtodo.spring_todo_api.model.dto.TodoDto;

import jakarta.persistence.Column;

import jakarta.persistence.Entity;

import jakarta.persistence.GeneratedValue;

import jakarta.persistence.GenerationType;

import jakarta.persistence.Id;

import jakarta.persistence.Table;

import jakarta.validation.Valid;

import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Size;

import lombok.AllArgsConstructor;

import lombok.Builder;

import lombok.Data;

import lombok.NoArgsConstructor;

@Table(name = "todos")
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Valid
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    @NotNull(message = "Title is required!")
    private String title;

    @Column(name = "description")
    @Size(max = 200, message = "Description size musn't be greater than 200 chars!")
    private String description;

    @Column(name = "user_id")
    private Integer userId;

    public static Todo fromDtoToEntity(TodoDto todoDto) {

        return Todo.builder()

                .id(todoDto.getId())

                .title(todoDto.getTitle())

                .description(todoDto.getDescription())

                .build();

    }

}
