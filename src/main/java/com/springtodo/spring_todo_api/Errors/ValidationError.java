package com.springtodo.spring_todo_api.Errors;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ValidationError {

    private Map<String, String> errors;

    private String uri;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy | HH:mm:ss")
    private Date timestamp;

    public ValidationError() {

        this.timestamp = new Date();

    }

    public Map<String, String> getErrors() {

        return this.errors;

    }

    public void setErrors(Map<String, String> errors) {

        this.errors = errors;

    }

    public String getUri() {

        return this.uri;

    }

    public void setUri(String uri) {

        this.uri = uri;

    }

    public Date getTimestamp() {

        return this.timestamp;

    }

    public void setTimestamp(Date timestamp) {

        this.timestamp = timestamp;

    }

}
