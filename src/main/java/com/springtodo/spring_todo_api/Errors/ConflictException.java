package com.springtodo.spring_todo_api.Errors;

import org.springframework.http.HttpStatus;

public class ConflictException extends ApiBaseException {

    public ConflictException(String message) {

        super(message);

    }

    public HttpStatus getStatusCode() {

        return HttpStatus.CONFLICT;

    }

}
