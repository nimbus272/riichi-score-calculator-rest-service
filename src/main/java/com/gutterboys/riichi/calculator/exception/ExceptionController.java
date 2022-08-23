package com.gutterboys.riichi.calculator.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.gutterboys.riichi.calculator.model.GenericResponse;

@ControllerAdvice
@RestController
public class ExceptionController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public GenericResponse handle(MethodArgumentNotValidException methodArgumentNotValidException) {
        GenericResponse response = new GenericResponse();
        response.setStatus("400");
        response.setMessage(methodArgumentNotValidException.getFieldError().toString());
        return response;
    }

}
