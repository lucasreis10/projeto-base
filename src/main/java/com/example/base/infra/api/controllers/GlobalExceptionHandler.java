package com.example.base.infra.api.controllers;

import com.example.base.application.usuario.excption.UsuarioOuSenhaIncorretosException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = UsuarioOuSenhaIncorretosException.class)
    public ResponseEntity<?> handleDomainException(final UsuarioOuSenhaIncorretosException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiError.from(exception));
    }

}

class ApiError {
    private String message;


    private ApiError(String message) {
        this.message = message;
    }

    static ApiError from(final UsuarioOuSenhaIncorretosException ex) {
        return new ApiError(ex.getMessage());
    }

    public String getMessage() {
        return message;
    }

}