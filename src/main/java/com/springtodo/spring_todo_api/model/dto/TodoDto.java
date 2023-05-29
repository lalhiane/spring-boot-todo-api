package com.springtodo.spring_todo_api.model.dto;

import com.springtodo.spring_todo_api.model.entity.Todo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TodoDto {

    private Integer id;

    private String title;

    private String description;

    private Integer userId;

    public static TodoDto fromEntityToDto(Todo entity) {

        return TodoDto.builder()

                .id(entity.getId())

                .title(entity.getTitle())

                .description(entity.getDescription())

                .userId(entity.getUserId())

                .build();

    }

}
