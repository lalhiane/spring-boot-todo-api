package com.springtodo.spring_todo_api.Errors;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class ApiExceptionHandller {

    @ExceptionHandler(ApiBaseException.class)
    public ResponseEntity<ErrorDetails> handleApiException(ApiBaseException ex, WebRequest request) {

        ErrorDetails errorDetails = new ErrorDetails(ex.getMessage(), request.getDescription(false));

        return new ResponseEntity<ErrorDetails>(errorDetails, HttpStatus.NOT_FOUND);

    }

    // @ExceptionHandler(MethodArgumentNotValidException.class)
    // public ResponseEntity<Map<String, String>> handleValidationExceptions(

    // MethodArgumentNotValidException ex

    // ) {

    // Map<String, String> errors = new HashMap<>();

    // ex.getBindingResult().getAllErrors().forEach((error) -> {

    // String fieldName = ((FieldError) error).getField();

    // String errorMessage = error.getDefaultMessage();

    // errors.put(fieldName, errorMessage);

    // });

    // return ResponseEntity.badRequest().body(errors);

    // }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleValidationExceptions(

            MethodArgumentNotValidException ex,

            WebRequest request

    ) {

        ValidationError validationError = new ValidationError();

        validationError.setUri(request.getDescription(false));

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {

            String fieldName = ((FieldError) error).getField();

            String errorMessage = error.getDefaultMessage();

            errors.put(fieldName, errorMessage);

        });

        validationError.setErrors(errors);

        return ResponseEntity.badRequest().body(validationError);

    }

}
