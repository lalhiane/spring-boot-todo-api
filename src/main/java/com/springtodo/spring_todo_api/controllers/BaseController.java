package com.springtodo.spring_todo_api.controllers;

import org.springframework.security.core.context.SecurityContextHolder;

import com.springtodo.spring_todo_api.security.user.User;

public abstract class BaseController {

    public User getCurrentUser() {

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return user;

    }

}
